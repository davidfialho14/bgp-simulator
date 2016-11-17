package simulators.gradualdeployment;

import core.Engine;
import core.Protocol;
import core.State;
import core.topology.ConnectedNode;
import simulators.DataCollector;
import simulators.Simulator;
import statemodifiers.StateModifiers;
import utils.PeriodicTimer;
import utils.RandomNodesSelector;

import java.util.Collection;

import static utils.PeriodicTimer.periodicTimer;

/**
 * Starts the simulation with an initial state and after a fixed periods of time a fixed number of nodes deploys
 * a new protocol. The new protocol, the time periods and the number of deploying nodes can be configured.
 */
public class GradualDeploymentSimulator extends Simulator {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final GradualDeploymentDataCollector dataCollector = new GradualDeploymentDataCollector();
    private final RandomNodesSelector nodesSelector;
    private PeriodicTimer deployPeriodicTimer;

    private final Protocol deployProtocol;
    private final int deployPeriod;
    private final int deployedNodeCount;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new simulator given an engine, initial state, the deploy time and the deploy protocol.
     *
     * @param engine            engine used for the simulation.
     * @param initialState      initial state.
     * @param deployProtocol    protocol to deploy at instant deployTime.
     * @param deployPeriod      time period to make deployments.
     * @param deployedNodeCount number of nodes to deploy new protocol in each period of time.
     */
    public GradualDeploymentSimulator(Engine engine, State initialState, Protocol deployProtocol,
                                      int deployPeriod, int deployedNodeCount) {
        super(engine, initialState);
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployedNodeCount = deployedNodeCount;

        nodesSelector = new RandomNodesSelector(initialState.getTopology().getNetwork());
    }

    /**
     * Gives access to the data collector used during the last simulation. The data collector will contain
     * the data collected.
     *
     * @return the data collector used to collect data in the last simulation.
     */
    @Override
    public DataCollector getDataCollector() {
        return dataCollector;
    }

    /**
     * (Template Method)
     * <p>
     * Called before starting a simulations instance (before running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary initial setup before a
     * simulation starts.
     */
    @Override
    protected void setup() {
        dataCollector.clear();
        dataCollector.register(engine);
        nodesSelector.reset();
        deployProtocol.reset();

        // use a timer to deploy the new protocol at the deploy time
        deployPeriodicTimer = periodicTimer(engine)
                .withPeriod(deployPeriod)
                .doOperation(() -> {
                    Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(deployedNodeCount);
                    for (ConnectedNode selectedNode : selectedNodes) {
                        StateModifiers.deployProtocol(state, selectedNode, deployProtocol);
                        dataCollector.notifyDeployment(selectedNode);
                    }
                })
                .start();
    }

    /**
     * (Template Method)
     * <p>
     * Called after finishing a simulation instance (after running {@link #simulate()}).
     * Subclasses should use this method to implement any necessary final cleanup after a
     * simulation finishes.
     */
    @Override
    protected void cleanup() {
        deployPeriodicTimer.stop(); // ensure the deploy timer is stopped and removed from the engine
        dataCollector.unregister();
    }

    /**
     * Returns an identification of the simulator.
     *
     * @return string "Gradual Deployment"
     */
    @Override
    public String toString() {
        return "Gradual Deployment";
    }

}
