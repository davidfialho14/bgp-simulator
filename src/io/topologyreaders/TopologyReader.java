package io.topologyreaders;


import core.Topology;
import io.topologyreaders.exceptions.TopologyParseException;

import java.io.Closeable;
import java.io.IOException;

/**
 * Base interface for a topology reader. A topology associates a topology with a routing policy.
 * Topology readers are responsible for reading a topology and the respective routing policy from
 * a given file format, returning a topology object associating the two. Each topology reader
 * implements a specific topology file format.
 */
public interface TopologyReader extends Closeable {

    /**
     * Reads a single topology. Subclasses must implement this method according to their supported
     * topology file format.
     *
     * @return topology associating the topology and policy read
     * @throws IOException      if an IO error occurs when reading the file
     * @throws TopologyParseException   if there is an error in the file format
     */
    Topology read() throws IOException, TopologyParseException;

}
