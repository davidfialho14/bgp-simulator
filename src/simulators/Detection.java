package simulators;

import core.Attribute;
import core.Path;
import core.topology.ConnectedNode;
import core.topology.Link;


/**
 * Stores information relative to one detection. One detection is associated with a detecting node, a cut-off link
 * and the cycle that originated the detection.
 */
public class Detection {

    private final ConnectedNode detectingNode;
    private final Link cutOffLink;
    private final Path cycle;
    private final Attribute initialAttribute;
    private final boolean falsePositive;

    public Detection(ConnectedNode detectingNode, Link cutOffLink, Path cycle, Attribute initialAttribute,
                     boolean falsePositive) {

        this.detectingNode = detectingNode;
        this.cutOffLink = cutOffLink;
        this.cycle = cycle;
        this.initialAttribute = initialAttribute;
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

    public Attribute getInitialAttribute() {
        return initialAttribute;
    }

    public boolean isFalsePositive() {
        return falsePositive;
    }

    @Override
    public String toString() {
        return "{" + detectingNode + ", " + cutOffLink + ", " + cycle + '}';
    }
}
