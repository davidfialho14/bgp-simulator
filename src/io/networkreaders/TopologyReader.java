package io.networkreaders;

import java.io.Closeable;

/**
 * Base interface for a topology reader. A topology associates a network with a routing policy.
 * Topology readers are responsible for reading a network and the respective routing policy from
 * a given file format, returning a topology object associating the two. Each topology reader
 * implements a specific topology file format.
 */
public interface TopologyReader extends AutoCloseable, Closeable {

    /**
     * Reads a single topology. Subclasses must implement this method according to their supported
     * topology file format.
     *
     * @return topology associating the network and policy read
     */
    Topology read();

}
