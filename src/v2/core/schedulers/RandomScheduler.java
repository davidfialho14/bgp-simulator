package v2.core.schedulers;

/**
 * Implementation of a scheduler where the delays are randomly uniformly distributed. It is based on the
 * RandomDelayGenerator implementation.
 */
public class RandomScheduler extends AbstractScheduler {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final RandomDelayGenerator randomDelayGenerator;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a RandomScheduler by assigning it a minimum and maximum delay for the messages.
     *
     * @param minDelay minimum message delay.
     * @param maxDelay maximum message delay.
     */
    public RandomScheduler(int minDelay, int maxDelay) {
        randomDelayGenerator = new RandomDelayGenerator(minDelay, maxDelay);
    }

    /**
     * Constructs a RandomScheduler by assigning it a minimum and maximum delay for the messages.
     * Forces the scheduler to use a specific seed to generate delays.
     *
     * @param minDelay minimum message delay.
     * @param maxDelay maximum message delay.
     * @param seed     seed to be used by the delay generator
     */
    public RandomScheduler(int minDelay, int maxDelay, long seed) {
        randomDelayGenerator = new RandomDelayGenerator(minDelay, maxDelay, seed);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the minimum delay (inclusive).
     *
     * @return the minimum delay (inclusive).
     */
    public int getMinDelay() {
        return randomDelayGenerator.getMin();
    }

    /**
     * Returns the maximum delay (inclusive).
     *
     * @return the maximum delay (inclusive).
     */
    public int getMaxDelay() {
        return randomDelayGenerator.getMax();
    }

    /**
     * Returns the seed used to generate the delays.
     *
     * @return the seed used to generate the delays.
     */
    public long getSeed() {
        return randomDelayGenerator.getSeed();
    }

    /**
     * Clears all messages from the scheduler and resets the seed of the delay generator.
     */
    @Override
    public void reset() {
        super.reset();
        randomDelayGenerator.reset();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Implementation of the "delay" method
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a delay value randomly uniformly distributed. It takes into account the time of the last
     * message sent by the link and therefore, the delay will always be equal or lower then the time of
     * arrival of the last message sent through the given link.
     *
     *
     * @return delay value.
     */
    @Override
    protected int delay() {
        return randomDelayGenerator.nextDelay();
    }

}
