package newsimulators.timeddeployment;

import io.newreporters.Reporter;
import newsimulators.Dataset;
import newsimulators.basic.BasicDataset;
import simulators.data.Detection;

import java.io.IOException;
import java.util.List;

/**
 * Contains all the data in a basic dataset and adds data specific to timed deployment simulations:
 *  - message count after deployment
 */
public class TimedDeploymentDataset implements Dataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Structures used to store the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final BasicDataset basicDataset = new BasicDataset();   // composition
    private int messageCountAfterDeployment = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data specific to timed deployment simulations
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the number of messages exchanged after deployment.
     *
     * @return number of messages exchanged after deployment
     */
    public int getMessageCountAfterDeployment() {
        return messageCountAfterDeployment;
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
     *  Public Interface - Methods to update the data specific to timed deployment simulations
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Counts a new message afters deployment.
     */
    public void addMessageAfterDeployment() {
        messageCountAfterDeployment++;
    }

    /**
     * Clears all data from the dataset.
     */
    public void clear() {
        basicDataset.clear();
        messageCountAfterDeployment = 0;
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
