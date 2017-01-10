package io;


import core.Destination;
import core.Topology;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static core.Destination.newDestination;

/**
 * Reader for the anycast files. The anycast files are line by line files.
 * Each line is formatted as: (destination)|(neighbor)|(label)
 */
public class AnycastReader implements Closeable {

    private Reader reader;
    private Topology topology;

    public AnycastReader(Reader reader, Topology topology) {
        this.reader = reader;
        this.topology = topology;
    }

    public AnycastReader(File anycastFile, Topology topology) throws FileNotFoundException {
        this(new FileReader(anycastFile), topology);
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
        final Map<Integer, Destination> destinations = new HashMap<>();

        // Create an anycast parser with an handler that adds each in-link to the found destinations
        // and keeps the destinations stored in the 'destinations' map

        final AnycastParser parser = new AnycastParser(reader, topology, (destinationId, neighbor, label) -> {
            Destination destination = destinations.computeIfAbsent(
                    destinationId, k -> newDestination(destinationId));

            destination.addInNeighbor(neighbor, label);
        });

        parser.parse();
        return destinations.values().toArray(new Destination[destinations.size()]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}

