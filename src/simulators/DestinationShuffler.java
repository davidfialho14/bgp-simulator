package simulators;


import core.Destination;
import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * A destination shuffler is responsible for storing an array of destinations and shuffling them randomly.
 * The shuffler stores the destination array. It ensures that all permutations generated are unique. It never
 * creates copies of the array and uses the initial array every time.
 *
 * In order to improve performance the shuffler uses a bloom filter to keep record of the permutations
 * already used. Due to its algorithm, the bloom filter might indicate a permutation has already been used
 * when that is not true. Consequently, it is possible that the shuffler can not generate all permutations.
 *
 * Once the destination shuffler is created and initialized with a set of destinations it sorts all the
 * destinations according to their IDs. This ensures that the first shuffle is done with the
 * destinations in the same order every time, allowing to reproduce the sequence of permutations if
 * provided the same seed.
 */
public class DestinationShuffler {

    private Destination[] destinations;
    private final Random random;    // will generate the random values required

    private BloomFilter<String> bloomFilter;    // keeps record of the permutations that have been used

    /**
     * Creates a destination shuffler initialized with an array of destinations. This array will be sorted
     * immediately after the shuffler is created. Uses the current time as the seed for the
     * random permutations.
     *
     * @param destinations  destination array to shuffle.
     */
    public DestinationShuffler(Destination[] destinations) {
        this.destinations = destinations;
        this.random = new Random();
        reset();
    }

    /**
     * Creates a destination shuffler initialized with an array of destinations. This array will be sorted
     * immediately after the shuffler is created. Forces the seed to be used to generate the
     * random permutations.
     *
     * @param destinations  destination array to shuffle.
     * @param seed          seed to use to generate the random permutations.
     */
    public DestinationShuffler(Destination[] destinations, long seed) {
        this.destinations = destinations;
        this.random = new Random(seed);
        reset();
    }

    /**
     * Returns the array of destination in its current state (permutation).
     *
     * @return the array of destination in its current state (permutation).
     */
    public Destination[] getDestinations() {
        return this.destinations;
    }

    /**
     * Shuffles the destination array randomly. The result of this operation is a new permutation of the
     * collection of destinations. This uses the Fisher-Yates shuffle algorithm. The algorithm produces an
     * unbiased permutation: every permutation is equally likely.
     */
    public void shuffle() {

        if (bloomFilter.getSize() < bloomFilter.getExpectedElements()) {
            throw new IllegalStateException("There is not more permutations possible!!");
        }

        int attempts = 0;
        while (true) {

            // weak condition to guarantee the loop finishes
            // this might not mean that there isn't actually more permutations
            if (attempts > bloomFilter.getExpectedElements()) {
                throw new IllegalStateException("Could not generate more permutations!!");
            }

            for (int i = destinations.length - 1; i >= 0; i--) {
                int randomValue = random.nextInt(i + 1);

                Destination temporary = destinations[randomValue];
                destinations[randomValue] = destinations[i];
                destinations[i] = temporary;
            }

            // The bloom filter uses the toString() method to hash the items
            // Arrays don't have the toString() method and therefore can not be used directly
            // That is why we need to convert the array into a string first
            String destinationsItem = Arrays.toString(destinations);

            if (!bloomFilter.contains(destinationsItem)) {
                bloomFilter.add(destinationsItem);
                break;
            }

            attempts++;
        }
    }

    /**
     * Resets the destination array. This means that all destinations will be sorted according to their
     * IDs.
     */
    private void reset() {
        int expectedElements = (int) CombinatoricsUtils.factorial(destinations.length);

        bloomFilter = new FilterBuilder(expectedElements, 0.1)
                .buildBloomFilter();

        // Sort the sequence!
        // This forces the initial state of the sequence before any simulation to be always the same,
        // independently of the order with which it is filled.
        Arrays.sort(destinations, Comparator.comparingInt(Destination::getId));
    }

    @Override
    public String toString() {
        return Arrays.toString(destinations);
    }

}
