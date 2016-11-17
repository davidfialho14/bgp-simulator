package io.networkreaders;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Topology reader factory to encapsulate the creation of Graphviz readers.
 */
public class GraphvizReaderFactory implements TopologyReaderFactory {

    /**
     * Creates a new GraphvizReader instance.
     *
     * @param topologyFile file to associate with the reader.
     * @return new instance GraphvizReader.
     * @throws FileNotFoundException if the reader fails to open the report file.
     */
    @Override
    public TopologyReader getTopologyReader(File topologyFile) throws FileNotFoundException {
        return new GraphvizReader(topologyFile);
    }

}
