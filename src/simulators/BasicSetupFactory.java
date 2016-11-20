package simulators;

import core.Destination;
import core.Engine;
import core.Topology;
import simulators.basic.BasicSetup;

/**
 * Creates instances of basic simulator for the configurations given in the factory constructor.
 */
public class BasicSetupFactory implements SetupFactory {

    /**
     * Creates a new simulator setup instance with the pre-configured parameters. The returned simulator
     * setup implementation depends on the factory implementation and on the parameters provided to the
     * factory.
     *
     * @param engine      engine used to simulate.
     * @param topology    topology to be simulated.
     * @param destination destination to simulate for.
     * @return new simulator setup instance.
     */
    @Override
    public Setup getSetup(Engine engine, Topology topology, Destination destination) {
        return new BasicSetup(engine, topology, destination);
    }

}
