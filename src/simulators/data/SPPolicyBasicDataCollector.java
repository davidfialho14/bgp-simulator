package simulators.data;

import io.reporters.Reporter;
import network.Node;
import policies.Path;
import policies.shortestpath.ShortestPathLabel;
import core.events.DetectEvent;

import java.io.IOException;
import java.util.Iterator;

/**
 * Extends the BasicDataCollector by adding the data set for the shortest path policy.
 */
public class SPPolicyBasicDataCollector extends BasicDataCollector {

    protected SPPolicyDataSet spPolicyDataSet = new SPPolicyDataSet();

    /**
     * Checks if a cycle's length is negative. Can only be used with the Shortest Path Policy.
     *
     * @param cycle cycle to check for.
     * @return true if the cycle's length is negative and false otherwise.
     */
    static boolean isNegative(Path cycle) {
        Iterator<Node> pathIterator = cycle.iterator();

        Node sourceNode = pathIterator.next();// node need to verify because a cycle must have always at least 3 nodes
        int cycleLength = 0;

        while (pathIterator.hasNext()) {
            Node destinationNode = pathIterator.next();

            // must be a shortest path label!!!
            ShortestPathLabel label = (ShortestPathLabel) sourceNode.getOutLink(destinationNode).getLabel();
            cycleLength += label.getLength();

            sourceNode = destinationNode;
        }

        return cycleLength >= 0;
    }

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to dump data to.
     */
    @Override
    public void dump(Reporter reporter) throws IOException {
        reporter.dump(basicDataSet, spPolicyDataSet);
    }

    /**
     * Clears all data from a data collector.
     */
    @Override
    public void clear() {
        super.clear();
        spPolicyDataSet.clear();
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = DetectEvent.getCycle(event);

        boolean isFalsePositive = isNegative(cycle);
        if (isFalsePositive) {
            spPolicyDataSet.addFalsePositive();
        }

        basicDataSet.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), cycle, isFalsePositive));
    }

}
