package simulators.timeddeployment;

import simulators.Dataset;

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
        messageCountAfterDeployment = 0;
    }

}
