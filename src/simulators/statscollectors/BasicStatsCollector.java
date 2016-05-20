package simulators.statscollectors;

import network.Link;
import network.Node;
import policies.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The basic stats collector collects some basic stats that most collectors have:
 * - network's node count
 * - network's link count
 * - total message count
 * - number of detecting nodes
 * - list of detecting nodes
 * - number of cut-off links
 * - links that were cut-off
 */
public class BasicStatsCollector {

    protected final int nodeCount;
    protected final int linkCount;

    // total number of simulations
    protected int simulationCount = 0;

    // stores the total message counts for each simulation
    protected List<Integer> totalMessageCounts = new ArrayList<>();

    // stores list of detections for each simulation
    protected List<List<Detection>> detections = new ArrayList<>();

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
     * Returns the number of simulations performed.
     *
     * @return number of simulations performed.
     */
    public int getSimulationCount() {
        return simulationCount;
    }

    /**
     * Returns the total message counts for each simulation in order (from first to last simulation).
     *
     * @return total message counts for each simulation in order (from first to last simulation).
     */
    public List<Integer> getTotalMessageCounts() {
        return totalMessageCounts;
    }

    /**
     * Returns the set of detecting nodes for each simulation in order (from first to last simulation).
     *
     * @return set of detecting nodes for each simulation in order (from first to last simulation).
     */
    public List<Set<Node>> getDetectingNodes() {
        return detections.stream()
                .map(simulationDetections -> simulationDetections.stream()
                        .map(Detection::getDetectingNode)
                        .collect(Collectors.toSet()))
                .collect(Collectors.toList());

    }

    /**
     * Returns the count of detecting nodes for each simulation in order (from first to last simulation).
     *
     * @return count of detecting nodes for each simulation in order (from first to last simulation).
     */
    public List<Integer> getDetectingNodesCounts() {
        return getDetectingNodes().stream()
                .map(Set::size)
                .collect(Collectors.toList());
    }

    /**
     * Returns the count of detecting nodes in the simulation with the given number.
     *
     * @param simulationNumber number of the simulation to get count for.
     * @return count of detecting nodes in the simulation with the given number.
     */
    public int getDetectingNodesCount(int simulationNumber) {
        return (int) detections.get(simulationNumber).stream()
                .map(Detection::getDetectingNode)
                .distinct()
                .count();
    }

    /**
     * Returns the list of cut-off links for each simulation in order (from first to last simulation).
     *
     * @return list of cut-off links for each simulation in order (from first to last simulation).
     */
    public List<List<Link>> getCutoffLinks() {
        return detections.stream()
                .map(simulationDetections -> simulationDetections.stream()
                        .map(Detection::getCutoffLink)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the count of cut-off links for each simulation in order (from first to last simulation).
     *
     * @return count of cut-off links for each simulation in order (from first to last simulation).
     */
    public List<Integer> getCutOffLinksCounts() {
        return getCutoffLinks().stream()
                .map(List::size)
                .collect(Collectors.toList());
    }

    /**
     * Returns the count of cut-off links in the simulation with the given number.
     *
     * @param simulationNumber number of the simulation to get count for.
     * @return count of cut-off links in the simulation with the given number.
     */
    public int getCutOffLinksCount(int simulationNumber) {
        return (int) detections.get(simulationNumber).stream()
                .map(Detection::getCutoffLink)
                .count();
    }

    /**
     * Returns the count of cut-off links for each simulation in order (from first to last simulation).
     *
     * @return count of cut-off links for each simulation in order (from first to last simulation).
     */
    public List<List<Detection>> getDetections() {
        return detections;
    }

    /**
     * Returns a list with all the detections that occurred in the simulation with the given number.
     *
     * @param simulationNumber number of the simulation to get detections for.
     * @return list with all the detections that occurred in the simulation with the given number
     */
    public List<Detection> getDetections(int simulationNumber) {
        return detections.get(simulationNumber);
    }

    /**
     * Indicates the collector that a new simulation will start. This method must be called before every
     * new simulation to tell the collector to start a new counter for the new simulation.
     */
    public void newSimulation() {
        simulationCount++;
        totalMessageCounts.add(0);  // start new count
        detections.add(new ArrayList<>());
    }

    /**
     * Should be invoked every time a new message happens.
     * It increases the total message count for the current simulation.
     */
    public void newMessage() {
        int currentCount = totalMessageCounts.get(simulationCount - 1);
        totalMessageCounts.set(simulationCount - 1, currentCount + 1);
    }

    /**
     * Should be invoked every time a new detection takes place. It adds the detecting node to the set of detecting
     * nodes if the node is not already there. Adds the cut-off link to the list of cut-off links.
     *
     * @param detectingNode node that detected.
     * @param cutoffLink    link that was cut off.
     * @param cycle         cycle that originated the detection.
     */
    public void newDetection(Node detectingNode, Link cutoffLink, Path cycle) {
        detections.get(simulationCount - 1).add(new Detection(detectingNode, cutoffLink, cycle));
    }
}
