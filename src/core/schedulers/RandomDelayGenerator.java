package core.schedulers;

import java.util.Random;

/**
 * Implements a random generator of delays.
 */
public class RandomDelayGenerator {

    private Random random = null;
    private int min;
    private int max;
    private Long forcedSeed = null;
    private long currentSeed;

    /**
     * Creates a generator without any seed of delay limits.
     */
    public RandomDelayGenerator() {
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
        reset();
    }

    /**
     * Creates a generator that generates values between the min and max - 1 (inclusive). If max <= 0 then an
     * IllegalArgumentException is thrown.
     *
     * @param min minimum value.
     * @param max maximum value.
     */
    public RandomDelayGenerator(int min, int max) {
        this();
        setMax(max);
        setMin(min);
    }

    /**
     * Creates a generator that generates values between the min and max - 1 (inclusive). If max <= 0 then an
     * IllegalArgumentException is thrown. Forces the seed of the random object to be the one given in the seed
     * argument.
     *
     * @param min   minimum value.
     * @param max   maximum value.
     * @param seed  seed to used with the random object
     */
    public RandomDelayGenerator(int min, int max, long seed) {
        this(min, max);
        this.forcedSeed = seed;
        reset();
    }

    /**
     * Returns the minimum delay (inclusive).
     *
     * @return the minimum delay (inclusive).
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the maximum delay (inclusive).
     *
     * @return the maximum delay (inclusive).
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the minimum delay (inclusive)
     *
     * @param min vlaue for the minimum delay.
     */
    public void setMin(int min) {
        if (min > max) {
            throw new IllegalArgumentException("min must be lower or equal to to max");
        }

        this.min = min;
    }

    /**
     * Sets the maximum delay (inclusive)
     *
     * @param max vlaue for the maximum delay.
     */
    public void setMax(int max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be higher or equal to min");
        }

        this.max = max;
    }

    /**
     * Returns the next delay value. It takes into account the specified interval.
     *
     * @return next delay value in the valid interval.
     */
    public int nextDelay() {
        return random.nextInt(max - min + 1)  + min;
    }

    /**
     * Returns the seed being currently used by the delay generator.
     *
     * @return the seed being currently used by the delay generator.
     */
    public long getSeed() {
        return currentSeed;
    }

    /**
     * Starts a new random with a new seed.
     */
    public void reset() {
        currentSeed = nextSeed();
        random = new Random(currentSeed);
    }

    /**
     * Returns the next seed for the random object. The first time it is called it returns the current time in
     * milliseconds. In the following calls return the sim of the next long value in the random object with the
     * current time in milliseconds. If the seed field is defined (not null) than it always returns the seed value.
     *
     * @return next seed for the random object.
     */
    private long nextSeed() {

        if (this.forcedSeed != null) {
            return this.forcedSeed;
        } else if (random == null) {
            return System.currentTimeMillis();
        } else {
            return System.currentTimeMillis() + random.nextLong();
        }
    }

}
