package simulators;

import core.Path;
import core.topology.ConnectedNode;
import core.topology.Link;


/**
 * Stores information relative to one detection. One detection is associated with a detecting node, a cut-off link
 * and the cycle that originated the detection.
 */
public class Detection {

    private ConnectedNode detectingNode;
    private Link cutOffLink;
    private Path cycle;
    private boolean falsePositive;

    public Detection(ConnectedNode detectingNode, Link cutOffLink, Path cycle, boolean falsePositive) {
        this.detectingNode = detectingNode;
        this.cutOffLink = cutOffLink;
        this.cycle = cycle;
        this.falsePositive = falsePositive;
    }

    public ConnectedNode getDetectingNode() {
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
