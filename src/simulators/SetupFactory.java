package simulators;

import core.Destination;
import core.Engine;
import core.Topology;

/**
 * Simulator factory creates instances of simulator setup classes depending on the given configurations.
 */
public interface SetupFactory {

    /**
     * Creates a new simulator setup instance with the pre-configured parameters. The returned simulator
     * setup implementation depends on the factory implementation and on the parameters provided to the
     * factory.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destination   destination to simulate for.
     * @return new simulator setup instance.
     */
    Setup getSetup(Engine engine, Topology topology, Destination destination);

}
