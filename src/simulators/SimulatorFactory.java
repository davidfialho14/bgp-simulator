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
     * Creates a new simulator instance with the pre-configured parameters. The returned simulator implementation
     * depends on the factory implementation and on the parameters provided to the factory.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destinationId destination node to simulate for.
     * @return new simulator instance.
     */
    Simulator getSimulator(Engine engine, Topology topology, int destinationId);

}
