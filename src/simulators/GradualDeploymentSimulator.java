package simulators;

import addons.protocolchangers.PeriodicProtocolChanger;
import core.Engine;
import core.Protocol;
import core.State;
import core.topology.Node;
import io.reporters.Reporter;
import simulators.data.GradualDeploymentDataCollector;
import utils.RandomNodesSelector;

import java.io.IOException;
import java.util.Collection;


/**
 * Simulates by starting all nodes with the BGP protocol and periodically deploys another protocol for a subset
 * of nodes until the simulation terminates or until the other protocol is deployed in all nodes.
 */
public class GradualDeploymentSimulator extends Simulator {

    protected final Protocol deployProtocol;  // protocol to deploy
    protected final long deployPeriod;
    protected final int deployedNodeCount;    // number of nodes to deploy new protocol in each period
    protected final RandomNodesSelector nodesSelector;

    protected final GradualDeploymentDataCollector gradualDeploymentDataCollector = new GradualDeploymentDataCollector();

    /**
     * Constructs a simulator by creating an initial state to be simulated.
     *
     * @param engine                    engine used for simulation.
     * @param topology                  topology to simulate.
     * @param destinationId             id of the destination node.
     * @param deployProtocol            initial protocol.
     * @param deployedNodePercentage    percentage of nodes to deploy in each period
     */
    public GradualDeploymentSimulator(Engine engine, State initialState, Protocol deployProtocol,
                                      long deployPeriod, int deployedNodeCount) {
        super(engine, initialState);
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployedNodeCount = deployedNodeCount;
        this.nodesSelector = new RandomNodesSelector(initialState.getTopology().getNetwork());

        this.gradualDeploymentDataCollector.register(engine);
    }

    @Override
    public void simulate() {
        deployProtocol.reset();
        gradualDeploymentDataCollector.clear();
        nodesSelector.reset();

        new PeriodicProtocolChanger(engine, state, deployPeriod) {
            @Override
            public void onTimeToChange() {
                Collection<Node> selectedNodes = nodesSelector.selectNodes(deployedNodeCount);
                for (Node selectedNode : selectedNodes) {
                    changeProtocol(selectedNode, deployProtocol);
                    gradualDeploymentDataCollector.setNodeDeployed(selectedNode);
                }
            }
        };

        super.simulate();
    }

    /**
     * Calls the reporter's generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        gradualDeploymentDataCollector.dump(reporter);
    }

    /**
     * Returns a string identifying the type of simulator.
     *
     * @return string "Gradual".
     */
    @Override
    public String toString() {
        return "Gradual";
    }

}
