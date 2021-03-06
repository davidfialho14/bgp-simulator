package io;


import com.google.common.collect.Sets;
import core.Destination;
import core.Topology;

import java.io.*;
import java.util.*;

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

    /**
     * Reads all destinations with the given IDs and only this destinations. If one or more of the given IDs
     * is not found then a DestinationNotFoundException is thrown.
     *
     * This is a more efficient way of reading a set of destinations without having to load all destinations
     * into memory.
     *
     * @param destinationIds IDs of the destinations to read.
     * @return array with the destinations read. Input order is not guaranteed.
     * @throws DestinationNotFoundException if at least one of the indicated destinations is not found.
     */
    public Destination[] readThis(Integer... destinationIds)
            throws IOException, ParseException, DestinationNotFoundException {

        final Set<Integer> wantedIds = new HashSet<>();
        Collections.addAll(wantedIds, destinationIds);

        return readThis(wantedIds);
    }

    /**
     * Reads all destinations with the given IDs and only this destinations. If one or more of the given IDs
     * is not found then a DestinationNotFoundException is thrown.
     *
     * This is a more efficient way of reading a set of destinations without having to load all destinations
     * into memory.
     *
     * @param destinationIds IDs of the destinations to read.
     * @return array with the destinations read. Input order is not guaranteed.
     * @throws DestinationNotFoundException if at least one of the indicated destinations is not found.
     */
    public Destination[] readThis(Collection<Integer> destinationIds)
            throws IOException, ParseException, DestinationNotFoundException {

        final Set<Integer> wantedIds = new HashSet<>();
        wantedIds.addAll(destinationIds);

        return readThis(wantedIds);
    }

    /**
     * Reads a single destination with the given ID and only this destination. If given ID is not found
     * then a DestinationNotFoundException is thrown.
     *
     * @param destinationId ID of the destination to read.
     * @return destination to read
     * @throws DestinationNotFoundException if at least one of the indicated destination is not found.
     */
    public Destination read(int destinationId)
            throws DestinationNotFoundException, IOException, ParseException {

        // we can access the first position of the returned array without checking because we are sure
        // the array has one destination, otherwise readThis() would throw DestinationNotFoundException
        return readThis(destinationId)[0];
    }

    private Destination[] readThis(Set<Integer> wantedIds) throws IOException, ParseException, DestinationNotFoundException {

        // map used to keep track of the destinations that have already been found
        final Map<Integer, Destination> destinations = new HashMap<>();

        // Create an anycast parser with an handler that considers only the links for destinations with the
        // given IDs and keeps the destinations stored in the 'destinations' map

        AnycastParser parser = new AnycastParser(reader, topology, (destinationId, neighbor, label) -> {

            if (wantedIds.contains(destinationId)) {
                Destination destination = destinations.computeIfAbsent(
                        destinationId, k -> newDestination(destinationId));

                destination.addInNeighbor(neighbor, label);
            }
        });

        parser.parse();

        // check if all wanted IDs were read
        Sets.SetView<Integer> missing = Sets.difference(wantedIds, destinations.keySet());

        if (!missing.isEmpty()) {
            throw new DestinationNotFoundException(missing);
        }

        return destinations.values().toArray(new Destination[destinations.size()]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}

