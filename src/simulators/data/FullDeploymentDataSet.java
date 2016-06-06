package simulators.data;

/**
 * Includes data specific to full deployment simulations:
 *  - message count after deployment
 */
public class FullDeploymentDataSet implements DataSet {

    private int messageCount = 0;

    public int getMessageCount() {
        return messageCount;
    }

    public void addMessage() {
        messageCount++;
    }

    /**
     * Clears all data from the dataset.
     */
    @Override
    public void clear() {
        messageCount = 0;
    }
}
