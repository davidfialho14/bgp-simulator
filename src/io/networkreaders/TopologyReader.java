package io.networkreaders;

import core.network.exceptions.NodeNotFoundException;
import io.InvalidTagException;
import io.networkreaders.exceptions.ParseException;

import java.io.Closeable;
import java.io.IOException;

/**
 * Base interface for a topology reader. A topology associates a network with a routing policy.
 * Topology readers are responsible for reading a network and the respective routing policy from
 * a given file format, returning a topology object associating the two. Each topology reader
 * implements a specific topology file format.
 */
public interface TopologyReader extends Closeable {

    /**
     * Reads a single topology. Subclasses must implement this method according to their supported
     * topology file format.
     *
     * @return topology associating the network and policy read
     * @throws IOException      if an IO error occurs when reading the file
     * @throws ParseException   if there is an error in the file format
     */
    Topology read() throws IOException, ParseException, InvalidTagException, NodeNotFoundException;

}
