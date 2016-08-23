package simulators;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import simulators.data.BasicDataCollector;
import simulators.data.SPPolicyBasicDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

public class InitialDeploymentSimulatorFactory implements SimulatorFactory {

    private final Protocol protocol;

    public InitialDeploymentSimulatorFactory(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * @param engine
     * @param topology
     * @param destinationId
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        BasicDataCollector dataCollector;

        if (isShortestPath(topology.getPolicy())) {
            dataCollector = new SPPolicyBasicDataCollector();

        } else {
            dataCollector = new BasicDataCollector();
        }

        State initialState = State.create(topology, destinationId, protocol);
        return new InitialDeploymentSimulator(engine, initialState, dataCollector);
    }
}
