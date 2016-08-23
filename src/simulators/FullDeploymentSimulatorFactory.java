package simulators;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;
import simulators.data.FullDeploymentDataCollector;
import simulators.data.SPPolicyFullDeploymentDataCollector;

import static policies.shortestpath.ShortestPathPolicy.isShortestPath;

/**
 * Simulator factory creates instances of FullDeploymentSimulator depending on the given configurations.
 */
public class FullDeploymentSimulatorFactory implements SimulatorFactory {

    private final Protocol deployProtocol;
    private final Integer deployTime;

    /**
     * Creates a new factory instance. It takes as arguments the specific parameters for this type of simulators.
     * The returned simulator implementation does not depend on this parameters.
     *
     * @param deployProtocol    protocol to deploy gradually.
     * @param deployTime        deployment time instant (at which the protocol is deployed to all nodes).
     */
    public FullDeploymentSimulatorFactory(Protocol deployProtocol, Integer deployTime) {
        this.deployProtocol = deployProtocol;
        this.deployTime = deployTime;
    }

    /**
     * Creates a new FullDeploymentSimulator instance with the pre-configured parameters. The returned simulator
     * implementation may depend on the arguments of the method.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destinationId destination node to simulate for.
     * @return new FullDeploymentSimulator instance.
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
