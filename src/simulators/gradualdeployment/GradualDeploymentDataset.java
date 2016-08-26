package simulators.gradualdeployment;

import core.topology.ConnectedNode;
import io.reporters.Reporter;
import simulators.Dataset;
import simulators.Detection;
import simulators.basic.BasicDataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores the data collected during a gradual deployment simulation. Stores the set of nodes that have deployed a new
 * protocols during one simulation instance.
 */
public class GradualDeploymentDataset implements Dataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final BasicDataset basicDataset = new BasicDataset();   // composition
    private final List<ConnectedNode> deployingNodes = new ArrayList<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data specific to gradual deployment
     *  simulations
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a collection with all deploying nodes.
     *
     * @return a collection with all deploying nodes.
     */
    public Collection<ConnectedNode> getDeployingNodes() {
        return deployingNodes;
    }

    /**
     * Returns the number of deploying nodes.
     *
     * @return the number of deploying nodes.
     */
    public int getDeployingNodesCount() {
        return this.deployingNodes.size();
    }

    /**
     * Gives access to the underlying basic dataset.
     *
     * @return the basic dataset storing the basic data.
     */
    public BasicDataset getBasicDataset() {
        return basicDataset;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to update the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets a node as a deploying node.
     *
     * @param node node to set as deploying node.
     */
    public void setAsDeployingNode(ConnectedNode node) {
        deployingNodes.add(node);
    }

    /**
     * Clears all data from the dataset.
     */
    public void clear() {
        basicDataset.clear();
        deployingNodes.clear();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Delegate methods for basic dataset (unchanged)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the total message count.
     *
     * @return total message count.
     */
    public int getTotalMessageCount() {
        return basicDataset.getTotalMessageCount();
    }

    /**
     * Returns the number of distinct nodes that detected at least once.
     *
     * @return number of distinct nodes with one detection.
     */
    public int getDetectingNodesCount() {
        return basicDataset.getDetectingNodesCount();
    }

    /**
     * Returns the number of cut-off links.
     *
     * @return number of cut-off links.
     */
    public int getCutOffLinksCount() {
        return basicDataset.getCutOffLinksCount();
    }

    /**
     * Returns a list with all the detections.
     *
     * @return a list with all the detections.
     */
    public List<Detection> getDetections() {
        return basicDataset.getDetections();
    }

    /**
     * Returns the total simulation time.
     *
     * @return total simulation time.
     */
    public long getSimulationTime() {
        return basicDataset.getSimulationTime();
    }

    /**
     * Sets the simulation time.
     *
     * @param time simulation time.
     */
    public void setSimulationTime(long time) {
        basicDataset.setSimulationTime(time);
    }

    /**
     * Counts a new message.
     */
    public void addMessage() {
        basicDataset.addMessage();
    }

    /**
     * Adds a new detection.
     *
     * @param detection detection to add.
     */
    public void addDetection(Detection detection) {
        basicDataset.addDetection(detection);
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
