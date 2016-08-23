package simulators;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;
import simulators.data.FullDeploymentDataCollector;
import simulators.data.SPPolicyFullDeploymentDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

public class FullDeploymentSimulatorFactory implements SimulatorFactory {

    private final Protocol deployProtocol;
    private final Integer deployTime;

    public FullDeploymentSimulatorFactory(Protocol deployProtocol, Integer deployTime) {
        this.deployProtocol = deployProtocol;
        this.deployTime = deployTime;
    }

    /**
     * @param engine
     * @param topology
     * @param destinationId
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        FullDeploymentDataCollector dataCollector;

        if (isShortestPath(topology.getPolicy())) {
            dataCollector = new SPPolicyFullDeploymentDataCollector();

        } else {
            dataCollector = new FullDeploymentDataCollector();
        }

        State initialState = State.create(topology, destinationId, new BGPProtocol());
        return new FullDeploymentSimulator(engine, initialState, deployProtocol, deployTime, dataCollector);
    }
}
