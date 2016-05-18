package io;

import io.stats.Stat;

import java.util.*;

/**
 * Implements the basics of a reporter like storing the multiple counts.
 * Subclasses only need to implement the generate method.
 */
public abstract class AbstractReporter implements Reporter {

    protected Map<Stat, List<Integer>> statsCounts = new LinkedHashMap<>();
    protected int countsLength = 0; // length for the stats counts (all stats must have the same amount of data)

    /**
     * Adds a new count for the stat.
     *
     * @param count new message count.
     */
    public void addCount(Stat stat, int count) {
        List<Integer> counts = statsCounts.get(stat);
        if (counts == null) {
            counts = new ArrayList<>();
            statsCounts.put(stat, counts);
        }

        counts.add(count);

        // update the counts length
        if (counts.size() > countsLength) {
            countsLength = counts.size();
        }
    }

}
