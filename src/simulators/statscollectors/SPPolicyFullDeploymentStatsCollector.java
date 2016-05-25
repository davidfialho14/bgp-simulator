package simulators.statscollectors;

import network.Link;
import network.Node;
import policies.Path;
import policies.shortestpath.ShortestPathLabel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SPPolicyFullDeploymentStatsCollector extends FullDeploymentStatsCollector {

    // stores the false positive counts for each simulation
    protected List<Integer> falsePositiveCounts = new ArrayList<>();

    /**
     * Constructs a basic stats collector. Initializes the node and link counts.
     *
     * @param nodeCount number of nodes of the original network.
     * @param linkCount number of links of the original network.
     */
    public SPPolicyFullDeploymentStatsCollector(int nodeCount, int linkCount) {
        super(nodeCount, linkCount);
    }

    /**
     * Returns the false positive counts for each simulation in order (from first to last simulation).
     *
     * @return false positive counts for each simulation in order (from first to last simulation).
     */
    public List<Integer> getFalsePositiveCounts() {
        return falsePositiveCounts;
    }

    /**
     * Returns the total message count in the simulation with the given number.
     *
     * @param simulationNumber number of the simulation to get count for.
     * @return total message count in the simulation with the given number.
     */
    public int getFalsePositiveCount(int simulationNumber) {
        return falsePositiveCounts.get(simulationNumber);
    }

    /**
     * Indicates the collector that a new simulation will start. This method must be called before every
     * new simulation to tell the collector to start a new counter for the new simulation.
     */
    @Override
    public void newSimulation() {
        super.newSimulation();
        falsePositiveCounts.add(0);
    }

    /**
     * Should be invoked every time a new detection takes place. It adds the detecting node to the set of detecting
     * nodes if the node is not already there. Adds the cut-off link to the list of cut-off links.
     *
     * @param detectingNode node that detected.
     * @param cutoffLink    link that was cut off.
     * @param cycle         cycle that originated the detection.
     */
    @Override
    public void newDetection(Node detectingNode, Link cutoffLink, Path cycle) {
        boolean isFalsePositive = isNegative(cycle);

        if (isFalsePositive) {
            incrementCount(falsePositiveCounts);
        }

        detections.get(simulationCount - 1).add(new Detection(detectingNode, cutoffLink, cycle, isFalsePositive));
    }

    /**
     * Checks if a cycle's length is negative. Can only be used with the Shortest Path Policy.
     *
     * @param cycle cycle to check for.
     * @return true if the cycle's length is negative and false otherwise.
     */
    protected boolean isNegative(Path cycle) {
        Iterator<Node> pathIterator = cycle.iterator();

        Node sourceNode = pathIterator.next();// node need to verify because a cycle must have always at least 3 nodes
        int cycleLength = 0;

        while (pathIterator.hasNext()) {
            Node destinationNode = pathIterator.next();

            // must be a shortest path label!!!
            ShortestPathLabel label = (ShortestPathLabel) sourceNode.getOutLink(destinationNode).getLabel();
            cycleLength += label.getLength();

            sourceNode = destinationNode;
        }

        return cycleLength >= 0;
    }
}
