package factories;

import core.topology.Topology;

/**
 * Interface that all topology factories should implement.
 */
public interface TopologyFactory {

    /**
     * Creates a topology instance initialized according to the topology ID given.
     *
     * @param id id of the topology to create
     * @return topology created.
     */
    Topology topology(int id);

}
