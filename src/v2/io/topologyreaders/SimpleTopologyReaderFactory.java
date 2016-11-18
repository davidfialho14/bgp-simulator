package v2.io.topologyreaders;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Topology reader factory to encapsulate the creation of simple topology readers.
 */
public class SimpleTopologyReaderFactory implements TopologyReaderFactory {

    /**
     * Creates a new SimpleTopologyReader instance.
     *
     * @param topologyFile file to associate with the reader.
     * @return new instance SimpleTopologyReader.
     * @throws FileNotFoundException if the reader fails to open the report file.
     */
    @Override
    public TopologyReader getTopologyReader(File topologyFile) throws FileNotFoundException {
        return new SimpleTopologyReader(topologyFile);
    }

}
