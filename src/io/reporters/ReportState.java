package io.reporters;


/**
 * Stores the current report state. This class is useful to keep record of simulation information to write in the
 * after summary report.
 */
public class ReportState {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  State information to keep
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int simulationCount = 0;
    private int detectionCount = 0;
    private int deploymentsCount = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Getters to access the stored information
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public int getSimulationCount() {
        return simulationCount;
    }

    public int getDetectionCount() {
        return detectionCount;
    }

    public int getDeploymentsCount() {
        return deploymentsCount;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Update methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void incrementSimulationCount() {
        simulationCount++;
    }

    public void addToDetectionCount(int value) {
        detectionCount++;
    }

    public void addToDeploymentsCount(int value) {
        deploymentsCount++;
    }

}
