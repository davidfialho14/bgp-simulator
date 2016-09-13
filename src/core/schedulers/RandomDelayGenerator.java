package core.schedulers;

import java.util.Random;

/**
 * Implements a random generator of delays.
 */
public class RandomDelayGenerator {

    private Random random = null;
    private int min;
    private int max;

    /**
     * Creates a generator without any
     */
    public RandomDelayGenerator() {
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
        reset();
    }

    /**
     * Creates a generator that generates values between the min and max - 1 (inclusive). If max <= 0 then an
     * IllegalArgumentException is thrown.
     * @param min minimum value.
     * @param max maximum value.
     */
    public RandomDelayGenerator(int min, int max) {
        this();
        setMax(max);
        setMin(min);
    }

    public void setMin(int min) {
        if (min > max) {
            throw new IllegalArgumentException("min must be lower or equal to to max");
        }

        this.min = min;
    }

    public void setMax(int max) {
        if (max < min) {
            throw new IllegalArgumentException("max must be higher or equal to min");
        }

        this.max = max;
    }

    /**
     * Returns the next delay value. It takes into account the specified interval.
     * @return next delay value in the valid interval.
     */
    public int nextDelay() {
        return random.nextInt(max - min + 1)  + min;
    }

    /**
     * Starts a new random with a new seed.
     */
    public void reset() {
        long seed = nextSeed();
        System.out.println("seed: " + seed);
        random = new Random(seed);
    }

    /**
     * Returns the next seed for the random object. The first time it is called it returns the current time in
     * milliseconds. In the following calls return the sim of the next long value in the random object with the
     * current time in milliseconds.
     *
     * @return next seed for the random object.
     */
    private long nextSeed() {

        if (random == null) {
            return System.currentTimeMillis();
        } else {
            return System.currentTimeMillis() + random.nextLong();
        }
    }

}
