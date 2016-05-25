package simulation.schedulers;

import java.util.Random;

/**
 * Implements a random generator of delays.
 */
public class RandomDelayGenerator {

    private Random random = new Random();
    private int min;
    private int max;

    /**
     * Creates a generator without any
     */
    public RandomDelayGenerator() {
        min = Integer.MIN_VALUE;
        max = Integer.MAX_VALUE;
    }

    /**
     * Creates a generator that generates values between the min and max - 1 (inclusive). If max <= 0 then an
     * IllegalArgumentException is thrown.
     * @param min minimum value.
     * @param max maximum value.
     */
    public RandomDelayGenerator(int min, int max) {
        if (max <= min) {
            throw new IllegalArgumentException("max must be higher then min");
        }

        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        if (max <= min) {
            throw new IllegalArgumentException("max must be higher then min");
        }

        this.min = min;
    }

    public void setMax(int max) {
        if (max <= min) {
            throw new IllegalArgumentException("max must be higher then min");
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

}
