package simulators.statscollectors;

import network.Link;
import network.Node;
import policies.Path;


/**
 * Stores information relative to one detection:
 *  - detecting node
 *  - cut-off link
 *  - cycle that originated the detection
 */
public class Detection {

    private Node detectingNode;
    private Link cutoffLink;
    private Path cycle;
    private boolean falsePositive;

    public Detection(Node detectingNode, Link cutoffLink, Path cycle, boolean falsePositive) {
        this.detectingNode = detectingNode;
        this.cutoffLink = cutoffLink;
        this.cycle = cycle;
        this.falsePositive = falsePositive;
    }

    public Detection(Node detectingNode, Link cutoffLink, Path cycle) {
        this(detectingNode, cutoffLink, cycle, false);
    }

    public Node getDetectingNode() {
        return detectingNode;
    }

    public Link getCutoffLink() {
        return cutoffLink;
    }

    public Path getCycle() {
        return cycle;
    }

    public boolean isFalsePositive() {
        return falsePositive;
    }

    @Override
    public String toString() {
        return "{" + detectingNode + ", " + cutoffLink + ", " + cycle + '}';
    }
}
