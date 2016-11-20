package io;


import core.Destination;
import core.Label;
import core.Router;
import core.Topology;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static core.protocols.DummyDetection.dummyDetection;

/**
 * Parser for anycast files. It parses an anycast file to find information for a specific destination ID
 */
public class AnycastParser {

    private AnycastParser() { } // should not be instantiated

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
