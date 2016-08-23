package simulators;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;

/**
 * Simulator factory creates instances of GradualDeploymentSimulator depending on the given configurations.
 */
public class GradualDeploymentSimulatorFactory implements SimulatorFactory {

    // specific parameters for this type of simulators
    private final Protocol deployProtocol;
    private final Integer deployPeriod;
    private final Integer deployPercentage;

    /**
     * Creates a new factory instance. It takes as arguments the specific parameters for this type of simulators.
     * The returned simulator implementation does not depend on this parameters.
     *
     * @param deployProtocol    protocol to deploy gradually.
     * @param deployPeriod      deployment period (at each period the protocol is deployed for a new set of nodes)
     * @param deployPercentage  percentage of nodes to deploy at each period (integer between 0 and 100)
     */
    public GradualDeploymentSimulatorFactory(Protocol deployProtocol, Integer deployPeriod, Integer deployPercentage) {
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployPercentage = deployPercentage;
    }

    /**
     * Creates a new GradualDeploymentSimulator instance with the pre-configured parameters. The returned simulator
     * implementation may depend on the arguments of the method.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destinationId destination node to simulate for.
     * @return new GradualDeploymentSimulator instance.
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        State initialState = State.create(topology, destinationId, new BGPProtocol());
        int deployedNodeCount = (int) Math.ceil(deployPercentage * topology.getNetwork().getNodeCount());

        return new GradualDeploymentSimulator(engine, initialState, deployProtocol, deployPeriod, deployedNodeCount);
    }
}
