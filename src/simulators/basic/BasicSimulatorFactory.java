package simulators.basic;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import simulators.Simulator;
import simulators.SimulatorFactory;

/**
 * Creates instances of basic simulator for the configurations given in the factory constructor.
 */
public class BasicSimulatorFactory implements SimulatorFactory {

    private final Protocol protocol;

    public BasicSimulatorFactory(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * Creates a new instance of basic simulator.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destinationId destination node to simulate for.
     * @return new instance of basic simulator.
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        return new BasicSimulator(engine, State.create(topology, destinationId, protocol));
    }
}
