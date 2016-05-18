package io;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the basics of a reporter like storing the multiple counts.
 * Subclasses only need to implement the generate method.
 */
public abstract class AbstractReporter implements Reporter {

    protected List<Integer> messageCounts = new ArrayList<>();
    protected List<Integer> detectionCounts = new ArrayList<>();
    protected List<Integer> detectingNodesCounts = new ArrayList<>();
    protected List<Integer> cutOffLinksCounts = new ArrayList<>();

    /**
     * Adds a new message count.
     *
     * @param count new message count.
     */
    @Override
    public void addMessageCount(int count) {
        messageCounts.add(count);
    }

    /**
     * Adds a new detection count.
     *
     * @param count new detection count.
     */
    @Override
    public void addDetectionCount(int count) {
        detectionCounts.add(count);
    }

    /**
     * Adds a new detecting nodes count.
     *
     * @param count new detecting nodes count.
     */
    @Override
    public void addDetectingNodesCount(int count) {
        detectingNodesCounts.add(count);
    }

    /**
     * Adds a new cut-off links count.
     *
     * @param count new cut-off links count.
     */
    @Override
    public void addCutOffLinksCount(int count) {
        cutOffLinksCounts.add(count);
    }
}
