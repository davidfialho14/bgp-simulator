package simulators.data;

import core.network.Node;

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
public class BasicDataSet implements DataSet {

    private int totalMessageCount = 0;
    private Set<Node> detectingNodes = new HashSet<>();    // stores all unique detecting nodes
    private int cutOffLinksCount = 0;
    private List<Detection> detections = new ArrayList<>();
    private long simulationTime = 0;

    // --- Methods to access the data ---

    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    public int getDetectingNodesCount() {
        return detectingNodes.size();
    }

    public int getCutOffLinksCount() {
        return cutOffLinksCount;
    }

    public List<Detection> getDetections() {
        return detections;
    }

    public long getSimulationTime() {
        return simulationTime;
    }

    // --- Methods to update the data

    public void setSimulationTime(long simulationTime) {
        this.simulationTime = simulationTime;
    }

    public void addMessage() {
        totalMessageCount++;
    }

    public void addDetection(Detection detection) {
        detections.add(detection);
        detectingNodes.add(detection.getDetectingNode());
        cutOffLinksCount++; // every detection cuts off a new link
    }

    /**
     * Clears all data from the dataset.
     */
    @Override
    public void clear() {
        totalMessageCount = 0;
        detectingNodes.clear();
        cutOffLinksCount = 0;
        detections.clear();
        simulationTime = 0;
    }
}
