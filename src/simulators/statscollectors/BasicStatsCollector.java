package simulators.statscollectors;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic stats collector collects some basic stats that most collectors have:
 *  - network's node count
 *  - network's link count
 *  - total message count
 */
public class BasicStatsCollector {

    protected final int nodeCount;
    protected final int linkCount;

    // total number of simulations
    protected int simulationCount = 0;

    // stores the total message counts for each simulation
    protected List<Integer> totalMessageCounts = new ArrayList<>();

    /**
     * Constructs a basic stats collector. Initializes the node and link counts.
     *
     * @param nodeCount number of nodes of the original network.
     * @param linkCount number of links of the original network.
     */
    public BasicStatsCollector(int nodeCount, int linkCount) {
        this.nodeCount = nodeCount;
        this.linkCount = linkCount;
    }

    /**
     * Returns the number of nodes of the original network.
     *
     * @return number of nodes of the original network.
     */
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * Returns the number of links of the original network.
     *
     * @return number of links of the original network.
     */
    public int getLinkCount() {
        return linkCount;
    }

    /**
     * Indicates the collector that a new simulation will start. This method must be called before every
     * new simulation to tell the collector to start a new counter for the new simulation.
     */
    public void newSimulation() {
        simulationCount++;
        totalMessageCounts.add(0);  // start new count
    }

    /**
     * Should be invoked every time a new message happens.
     * It increases the total message count for the current simulation.
     */
    public void newMessage() {
        int currentCount = totalMessageCounts.get(simulationCount - 1);
        totalMessageCounts.set(simulationCount - 1, currentCount + 1);
    }
}
