package simulators.gradualdeployment;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;
import simulators.Simulator;
import simulators.SimulatorFactory;

/**
 * Creates instances of gradual deployment simulator with the configurations given in the factory constructor.
 */
public class GradualDeploymentSimulatorFactory implements SimulatorFactory {

    private final Protocol deployProtocol;
    private final int deployPeriod;
    private final int deployPercentage;

    /**
     * Creates a new factory instance. It takes as arguments the specific parameters for this type of simulators.
     * The returned simulator implementation does not depend on this parameters.
     *
     * @param deployProtocol    deployProtocol to deploy gradually.
     * @param deployPeriod      period to have new nodes deploying a new protocol.
     * @param deployPercentage  percentage of network nodes to deploy new protocol each period.
     */
    public GradualDeploymentSimulatorFactory(Protocol deployProtocol, int deployPeriod, int deployPercentage) {
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployPercentage = deployPercentage;
    }

    /**
     * Creates a new instance of gradual deployment simulator with the pre-configured parameters.
     *
     * @param engine        engine used to simulate.
     * @param topology      topology to be simulated.
     * @param destinationId destination node to simulate for.
     * @return new instance of basic simulator.
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        // always initiate with the BGP deployProtocol!
        State initialState = State.create(topology, destinationId, new BGPProtocol());
        int deployedNodeCount = (int) Math.ceil(deployPercentage / 100.0 * topology.getNetwork().getNodeCount());

        return new GradualDeploymentSimulator(engine, initialState, deployProtocol, deployPeriod, deployedNodeCount);
    }

}
