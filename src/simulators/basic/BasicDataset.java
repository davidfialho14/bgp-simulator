package simulators.basic;

import core.topology.Node;
import io.reporters.Reporter;
import simulators.Dataset;
import simulators.Detection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Stores the following data:
 *  - total message count
 *  - detecting nodes count
 *  - cut-off links count
 *  - detections
 */
public class BasicDataset implements Dataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Structures used to store the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int totalMessageCount = 0;
    private Set<Node> detectingNodes = new HashSet<>();    // stores all unique detecting nodes
    private int cutOffLinksCount = 0;
    private List<Detection> detections = new ArrayList<>();
    private long simulationTime = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the total message count.
     *
     * @return total message count.
     */
    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    /**
     * Returns the number of distinct nodes that detected at least once.
     *
     * @return number of distinct nodes with one detection.
     */
    public int getDetectingNodesCount() {
        return detectingNodes.size();
    }

    /**
     * Returns the number of cut-off links.
     *
     * @return number of cut-off links.
     */
    public int getCutOffLinksCount() {
        return cutOffLinksCount;
    }

    /**
     * Returns a list with all the detections.
     *
     * @return a list with all the detections.
     */
    public List<Detection> getDetections() {
        return detections;
    }

    /**
     * Returns the total simulation time.
     *
     * @return total simulation time.
     */
    public long getSimulationTime() {
        return simulationTime;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to update the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets the simulation time.
     *
     * @param time simulation time.
     */
    public void setSimulationTime(long time) {
        this.simulationTime = time;
    }

    /**
     * Counts a new message.
     */
    public void addMessage() {
        totalMessageCount++;
    }

    /**
     * Adds a new detection.
     *
     * @param detection detection to add.
     */
    public void addDetection(Detection detection) {
        detections.add(detection);
        detectingNodes.add(detection.getDetectingNode());
        cutOffLinksCount++; // every detection cuts off a new link
    }

    /**
     * Clears all data from the dataset.
     */
    public void clear() {
        totalMessageCount = 0;
        detectingNodes.clear();
        cutOffLinksCount = 0;
        detections.clear();
        simulationTime = 0;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Visited report method
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Reports the current collected data using the given reporter implementation.
     *
     * @param reporter reporter implementation to be used.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        reporter.writeData(this);
    }

}
