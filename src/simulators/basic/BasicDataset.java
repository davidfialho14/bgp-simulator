package simulators.basic;


import core.Router;
import simulators.Dataset;
import simulators.DetectionData;

import java.util.*;

/**
 * Stores the following data:
 *  - total message count
 *  - detecting routers count
 *  - cut-off links count
 *  - false positives count
 *  - detections
 *  - last message times
 *  - protocol terminated
 */
public class BasicDataset implements Dataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Structures used to store the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private long simulationSeed = 0;
    private int totalMessageCount = 0;
    private Set<Router> detectingRouters = new HashSet<>();    // stores all unique detecting routers
    private int cutOffLinksCount = 0;
    private List<DetectionData> detections = new ArrayList<>();
    private long simulationTime = 0;
    private int falsePositiveCount = 0;
    // stores the times of the last message of each router
    private Map<Router, Long> lastMessageTimes = new HashMap<>();
    private boolean protocolTerminated = true;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the seed of the simulation which generated the current data.
     *
     * @return the seed of the simulation which generated the current data.
     */
    public long getSimulationSeed() {
        return simulationSeed;
    }

    /**
     * Returns the label for the data property Seed.
     *
     * @return the label for the data property Seed
     */
    public String getSimulationSeedLabel() {
        return "Seed";
    }

    /**
     * Returns the total message count.
     *
     * @return total message count.
     */
    public int getTotalMessageCount() {
        return totalMessageCount;
    }

    /**
     * Returns the label for the data property Total Message Count.
     *
     * @return the label for the data property Total Message Count
     */
    public String getTotalMessageCountLabel() {
        return "Total Message Count";
    }

    /**
     * Returns the number of distinct routers that detected at least once.
     *
     * @return number of distinct routers with one detection.
     */
    public int getDetectingRoutersCount() {
        return detectingRouters.size();
    }

    /**
     * Returns the label for the data property Detecting Routers Count.
     *
     * @return the label for the data property Detecting Routers Count
     */
    public String getDetectingRoutersCountLabel() {
        return "Detecting Routers Count";
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
     * Returns the label for the data property Cut-Off Links Count.
     *
     * @return the label for the data property Cut-Off Links Count
     */
    public String getCutOffLinksCountLabel() {
        return "Cut-Off Links Count";
    }

    /**
     * Returns a list with all the detections.
     *
     * @return a list with all the detections.
     */
    public List<DetectionData> getDetections() {
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

    /**
     * Returns the label for the data property Time.
     *
     * @return the label for the data property Time
     */
    public String getSimulationTimeLabel() {
        return "Time";
    }

    /**
     * Returns the number of false positives.
     *
     * @return number of false positives.
     */
    public int getFalsePositiveCount() {
        return falsePositiveCount;
    }

    /**
     * Returns the label for the data property False Positive Count.
     *
     * @return the label for the data property False Positive Count
     */
    public String getFalsePositiveCountLabel() {
        return "False Positive Count";
    }

    /**
     * Returns the last message times for each router.
     *
     * @return the last message times for each router
     */
    public Map<Router, Long> getLastMessageTimes() {
        return lastMessageTimes;
    }

    /**
     * Returns true if the protocol terminated or false if otherwise.
     *
     * @return true if the protocol terminated or false if otherwise
     */
    public boolean didProtocolTerminate() {
        return protocolTerminated;
    }

    /**
     * Returns the label for the data property 'did protocol terminate'.
     *
     * @return the label for the data property 'did protocol terminate'
     */
    public String getDidProtocolTerminateLabel() {
        return "Terminated";
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to update the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets a new simulation seed. This should be set for each simulation.
     *
     * @param simulationSeed seed corresponding to the simulation to which the data belongs to.
     */
    public void setSimulationSeed(long simulationSeed) {
        this.simulationSeed = simulationSeed;
    }

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
    public void addDetection(DetectionData detection) {
        detections.add(detection);
        detectingRouters.add(detection.getDetectingRouter());
        cutOffLinksCount++; // every detection cuts off a new link

        if (detection.isFalsePositive()) {
            falsePositiveCount++;
        }
    }

    /**
     * Sets the last message time of the given router to the given time.
     *
     * @param router  router to set last message time for
     * @param time  new time to set
     */
    public void setLastMessageTime(Router router, long time) {
        lastMessageTimes.put(router, time);
    }

    /**
     * Sets teh flag to indicate if the protocol terminated.
     *
     * @param terminated  true to terminated and false otherwise.
     */
    public void setProtocolTerminated(boolean terminated) {
        this.protocolTerminated = terminated;
    }

    /**
     * Clears all data from the dataset.
     */
    public void clear() {
        simulationSeed = 0;
        totalMessageCount = 0;
        detectingRouters.clear();
        cutOffLinksCount = 0;
        detections.clear();
        simulationTime = 0;
        falsePositiveCount = 0;
        lastMessageTimes.clear();
        protocolTerminated = true;
    }

}
