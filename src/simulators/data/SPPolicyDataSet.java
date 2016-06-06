package simulators.data;

/**
 * Stores the following information relative only to simulations with the Shortest Path Policy:
 *  - false positive count
 */
public class SPPolicyDataSet implements DataSet {

    private int falsePositiveCount = 0;

    /**
     * Returns the current false positive count.
     *
     * @return the current false positive count.
     */
    public int getFalsePositiveCount() {
        return falsePositiveCount;
    }

    /**
     * Increments the false positive count.
     */
    public void addFalsePositive() {
        falsePositiveCount++;
    }

    /**
     * Clears all data from the dataset.
     */
    @Override
    public void clear() {
        falsePositiveCount = 0;
    }

}
