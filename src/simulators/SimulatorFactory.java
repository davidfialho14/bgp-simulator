package simulators;

import core.Engine;
import core.topology.Topology;

/**
 * Simulator factory creates instances of simulator classes depending on the given configurations.
 */
public interface SimulatorFactory {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Factory methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * @param engine
     * @param topology
     * @param destinationId
     */
    Simulator getSimulator(Engine engine, Topology topology, int destinationId);

}
