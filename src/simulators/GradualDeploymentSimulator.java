package simulators;

import addons.protocolchangers.PeriodicProtocolChanger;
import io.reporters.Reporter;
import network.Network;
import network.Node;
import protocols.BGPProtocol;
import protocols.Protocol;
import simulation.Engine;
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
     * @param engine         engine used for simulation.
     * @param network        network to simulate.
     * @param destinationId  id of the destination node.
     * @param deployProtocol initial protocol.
     * @param deployedNodePercentage
     */
    public GradualDeploymentSimulator(Engine engine, Network network, int destinationId, Protocol deployProtocol,
                                      long deployPeriod, double deployedNodePercentage) {
        super(engine, network, destinationId, new BGPProtocol());
        this.deployProtocol = deployProtocol;
        this.deployPeriod = deployPeriod;
        this.deployedNodeCount = (int) Math.ceil(deployedNodePercentage * network.getNodeCount());
        this.nodesSelector = new RandomNodesSelector(network);
    }

    @Override
    public void simulate() {
        deployProtocol.reset();
        gradualDeploymentDataCollector.clear();

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

}
