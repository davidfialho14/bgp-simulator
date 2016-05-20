package simulators.statscollectors;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds the number of messages sent after deployment to the basic stats collector.
 */
public class FullDeploymentStatsCollector extends BasicStatsCollector {

    private boolean deployed = false;   // indicates the deployment was already done.

    // stores the message counts after deployment for each simulation
    protected List<Integer> messageCountsAfterDeployment = new ArrayList<>();

    /**
     * Constructs a basic stats collector. Initializes the node and link counts.
     *
     * @param nodeCount number of nodes of the original network.
     * @param linkCount number of links of the original network.
     */
    public FullDeploymentStatsCollector(int nodeCount, int linkCount) {
        super(nodeCount, linkCount);
    }

    /**
     * Notifies the stats collector that the deployment was already done. Should be called right after the deployment.
     * This marks the time after which it should start to count message after deployment.
     */
    public void notifyDeployment() {
        deployed = true;
    }

    /**
     * Returns the message counts after deployment for each simulation in order (from first to last simulation).
     *
     * @return message counts after deployment for each simulation in order (from first to last simulation).
     */
    public List<Integer> getMessageCountsAfterDeployment() {
        return messageCountsAfterDeployment;
    }

    /**
     * Returns the message count after deployment for one simulation.
     *
     * @return message count after deployment for one simulation.
     */
    public int getMessageCountAfterDeployment(int simulationNumber) {
        return messageCountsAfterDeployment.get(simulationNumber);
    }

    /**
     * Indicates the collector that a new simulation will start. This method must be called before every
     * new simulation to tell the collector to start a new counter for the new simulation.
     */
    @Override
    public void newSimulation() {
        super.newSimulation();
        messageCountsAfterDeployment.add(0);
        deployed = false;
    }

    /**
     * Should be invoked every time a new message happens.
     * It increases the total message count for the current simulation.
     * If the deployment was already done it counts the message exchanged after deployment.
     */
    @Override
    public void newMessage() {
        super.newMessage();
        if (deployed) {
            int currentCount = messageCountsAfterDeployment.get(simulationCount - 1);
            messageCountsAfterDeployment.set(simulationCount - 1, currentCount + 1);
        }
    }
}
