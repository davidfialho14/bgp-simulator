package simulators.data;

import network.Link;
import network.Node;
import core.Path;


/**
 * Stores information relative to one detection. One detection is associated with a detecting node, a cut-off link
 * and the cycle that originated the detection.
 */
public class Detection {

    private Node detectingNode;
    private Link cutOffLink;
    private Path cycle;
    private boolean falsePositive;

    public Detection(Node detectingNode, Link cutOffLink, Path cycle, boolean falsePositive) {
        this.detectingNode = detectingNode;
        this.cutOffLink = cutOffLink;
        this.cycle = cycle;
        this.falsePositive = falsePositive;
    }

    public Detection(Node detectingNode, Link cutOffLink, Path cycle) {
        this(detectingNode, cutOffLink, cycle, false);
    }

    public Node getDetectingNode() {
        return detectingNode;
    }

    public Link getCutOffLink() {
        return cutOffLink;
    }

    public Path getCycle() {
        return cycle;
    }

    public boolean isFalsePositive() {
        return falsePositive;
    }

    @Override
    public String toString() {
        return "{" + detectingNode + ", " + cutOffLink + ", " + cycle + '}';
    }
}
