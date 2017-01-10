package io;


import core.Destination;
import core.Label;
import core.Router;
import core.Topology;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static core.Destination.newDestination;

/**
 * Reader for the anycast files. The anycast files are line by line files.
 * Each line is formatted as: (destination)|(neighbor)|(label)
 */
public class AnycastReader implements Closeable {

    private final Topology topology;
    private final BufferedReader reader;

    public AnycastReader(Topology topology, Reader reader) {
        this.topology = topology;
        this.reader = new BufferedReader(reader);
    }

    public AnycastReader(Topology topology, File anycastFile) throws FileNotFoundException {
        this(topology, new FileReader(anycastFile));
    }

    /**
     * Reads all destinations from the anycast file. The destination are directly initialized with the
     * in-links specified in the anycast file.
     *
     * @return array with the destinations read.
     * @throws ParseException if the anycast file format is incorrect or if some line argument is incorrect.
     * @throws IOException if an IO error occurs.
     */
    public Destination[] readAll() throws ParseException, IOException {

        // map used to keep track of the destinations that have already been found
        Map<Integer, Destination> destinations = new HashMap<>();

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

            Destination destination = destinations.computeIfAbsent(
                    destinationId, k -> newDestination(destinationId));

            destination.addInNeighbor(neighbor, label);
        }

        return destinations.values().toArray(new Destination[destinations.size()]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}

