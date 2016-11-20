package io.topologyreaders;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * Topology reader factory creates topology readers based on the factory implementation. When a reader is created the
 * input file might be open right away. This prevents a method from receiving a reader as parameter and use a try()
 * block to ensure the reader is closed properly. Reader factories allow to pass the reader implementation as a
 * parameter and to create an instance when necessary inside a try() block.
 */
public interface TopologyReaderFactory {

    /**
     * Creates a new topology reader instance. The type of topology reader instance returned depends on the factory
     * implementation.
     *
     * @param topologyFile file to associate with the reader.
     * @return new instance TopologyReader.
     * @throws FileNotFoundException if the reader fails to open the report file.
     */
    TopologyReader getTopologyReader(File topologyFile) throws FileNotFoundException;

}
