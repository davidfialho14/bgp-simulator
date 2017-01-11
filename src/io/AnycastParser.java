package io;


import core.Destination;
import core.Label;
import core.Router;
import core.Topology;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static core.protocols.DummyDetection.dummyDetection;


/**
 * The anycast link handler is called every time a new anycast link is parsed. Since the anycast file
 * contains a link in each line this is the same as saying the handler will be called for each non-empty line.
 *
 * This class is meant to be overridden to implement the wanted behavior once a new link is parsed.
 */
@FunctionalInterface
interface AnycastLinkHandler {

    /**
     * Invoked every time a new link is parsed.
     *
     * Notice we pass the destination ID and not a destination object. This is because the same destination
     * ID might appear multiple times in the file (once for each of its in-links) and probably the handler
     * does not want to create a destination object every time.
     *
     * @param destinationId ID of the destination.
     * @param neighbor      neighbour router from the topology.
     * @param label         parsed label for he link between the neighbor and the destination.
     */
    void onNewLink(int destinationId, Router neighbor, Label label);

}

/**
 * Parser for anycast files. It parses an anycast file to find information for a specific destination ID
 */
public class AnycastParser implements Closeable {

    private final BufferedReader reader;
    private final Topology topology;
    private AnycastLinkHandler handler;

    /**
     * Creates an anycast parser with an already initialized reader. Mostly used for testing.
     *
     * @param reader    reader for the anycast file
     * @param handler   handler called for each parsed link.
     * @param topology  topology to get neighbors from.
     */
    public AnycastParser(Reader reader, Topology topology, AnycastLinkHandler handler) {
        this.reader = new BufferedReader(reader);
        this.handler = handler;
        this.topology = topology;
    }

    /**
     * Opens the anycast file.
     *
     * @param anycastFile   anycast file to be parsed.
     * @param handler       handler called for each parsed link.
     * @param topology      topology to get neighbors from.
     */
    public AnycastParser(File anycastFile, Topology topology, AnycastLinkHandler handler) throws FileNotFoundException {
        this(new FileReader(anycastFile), topology, handler);
    }

    /**
     * Parses the anycast file and calls the associated handler (constructor) every time a
     * new link is parsed.
     */
    public void parse() throws IOException, ParseException {

        String line; int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            lineCount++;
            if (line.isEmpty()) continue;   // ignore empty lines

            // split the line into the destination ID, neighbor ID, and label tag
            String[] lineArgs = line.split("\\|");

            // parse the line arguments
            int destinationId, neighborId;
            Label label;
            try {
                destinationId = Integer.parseInt(lineArgs[0]);
                neighborId = Integer.parseInt(lineArgs[1]);
                label = topology.getPolicy().createLabel(lineArgs[2]);

                if (destinationId < 0 || neighborId < 0) {
                    // IDs must be greater than zero - redirect to central catch
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                throw new ParseException("IDs must be integer numbers greater then 0", lineCount);

            } catch (InvalidPolicyTagException e) {
                throw new ParseException("Label tag is not valid", lineCount);
            }

            Router neighbor = topology.getRouter(neighborId);

            if (neighbor == null) {
                throw new ParseException("There is no node in the topology with the ID corresponding " +
                        "to the neighbor ID", lineCount);
            }

            handler.onNewLink(destinationId, neighbor, label);
        }

    }

    /**
     * Parses the given anycast file to find relevant information to construct a destination router.
     *
     * @param anycastFile   path to anycast file to parse.
     * @param topology      topology.
     * @param destinationID ID of the destination to look for.
     * @return destination router parsed from the file.
     * @throws DestinationNotFoundException if the destination ID is not found in the anycast file.
     */
    public static Destination parseDestination(String anycastFile, Topology topology, int destinationID)
            throws DestinationNotFoundException, IOException {

        // create fake destination router
        Destination destination = new Router(destinationID, 0, dummyDetection());

        try( Stream<String> lines = Files.lines(Paths.get(anycastFile)) ) {

            // get only lines containing in-links to the destination router
            Stream<String> destinationLines = lines.filter(line -> line.startsWith(destinationID + "|"));

            destinationLines.forEach(line -> {
                ParsedLine parsedLine = parseDestinationLine(line, topology);

                if (parsedLine != null) {
                    destination.addInNeighbor(parsedLine.neighbor, parsedLine.label);
                }
            });
        }

        if (destination.getInNeighborCount() == 0) {
            // the destination must have in-neighbors, otherwise, it means the anycast file did not contain
            // in-links for the destination
            throw new DestinationNotFoundException(destinationID);
        }

        return destination;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    /**
     * Parsed line contains the information obtain when the line is parsed. It works as a simple named tuple
     * like in python!
     */
    private static class ParsedLine {
        private final Router neighbor;
        private final Label label;

        public ParsedLine(Router neighbor, Label label) {
            this.neighbor = neighbor;
            this.label = label;
        }
    }

    /**
     * Parses a destination line to find the neighbor and the label. If the line is invalid then it returns
     * null.
     *
     * @param line      destination line to parse.
     * @param topology  topology to which the neighbor should belong to.
     * @return null if the line is invalid or a parsed line containing the parsed neighbor and label.
     */
    private static ParsedLine parseDestinationLine(String line, Topology topology) {
        // the line format is <destination>|<neighbour>|<label>
        String[] lineArgs = line.split("\\|");

        try {
            int neighborId = Integer.parseInt(lineArgs[1]);
            Label label = topology.getPolicy().createLabel(lineArgs[2]);

            Router neighbor = topology.getRouter(neighborId);
            if (neighbor == null) return null;  // invalid line!

            return new ParsedLine(neighbor, label);

        } catch (NumberFormatException | InvalidPolicyTagException e) {
            // invalid line!
            return null;
        }

    }

}
