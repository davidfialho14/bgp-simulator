package simulators.timeddeployment;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Topology;
import protocols.BGPProtocol;
import simulators.Simulator;
import simulators.SimulatorFactory;

/**
 * Creates instances of timed deployment simulator with the configurations given in the factory constructor.
 */
public class TimedDeploymentSimulatorFactory implements SimulatorFactory {

    private final Protocol deployProtocol;
    private final long deployTime;

    /**
     * Creates a new factory instance. It takes as arguments the specific parameters for this type of simulators.
     * The returned simulator implementation does not depend on this parameters.
     *
     * @param deployProtocol    deployProtocol to deploy gradually.
     * @param deployTime        deployment time instant (at which the deployProtocol is deployed to all nodes).
     */
    public TimedDeploymentSimulatorFactory(Protocol deployProtocol, long deployTime) {
        this.deployProtocol = deployProtocol;
        this.deployTime = deployTime;
    }

    /**
     * Creates a new instance of timed deployment simulator with the pre-configured parameters.
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

        return new TimedDeploymentSimulator(engine, initialState, deployTime, deployProtocol);
    }

}
