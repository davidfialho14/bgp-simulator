package newsimulators.timeddeployment;

import io.newreporters.Reporter;
import newsimulators.basic.BasicDataset;

import java.io.IOException;

/**
 * Includes data specific to timed deployment simulations:
 *  - message count after deployment
 */
public class TimedDeploymentDataset extends BasicDataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Structures used to store the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private int messageCountAfterDeployment = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data
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
     *  Public Interface - Methods to update the data
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
