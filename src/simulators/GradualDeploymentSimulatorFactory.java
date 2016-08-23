package simulators;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;

public class GradualDeploymentSimulatorFactory implements SimulatorFactory {


    private final Protocol deployProtocol;
    private final Integer deployPeriod;
    private final Integer deployPercentage;

    public GradualDeploymentSimulatorFactory(Protocol deployProtocol, Integer deployPeriod, Integer deployPercentage) {
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployPercentage = deployPercentage;
    }

    /**
     * @param engine
     * @param topology
     * @param destinationId
     */
    @Override
    public Simulator getSimulator(Engine engine, Topology topology, int destinationId) {
        State initialState = State.create(topology, destinationId, new BGPProtocol());
        int deployedNodeCount = (int) Math.ceil(deployPercentage * topology.getNetwork().getNodeCount());

        return new GradualDeploymentSimulator(engine, initialState, deployProtocol, deployPeriod, deployedNodeCount);
    }
}
