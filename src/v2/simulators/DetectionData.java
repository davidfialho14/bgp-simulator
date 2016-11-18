package v2.simulators;

import v2.core.Attribute;
import v2.core.Link;
import v2.core.Path;
import v2.core.Router;

/**
 * Stores information relative to one detection. One detection is associated with a detecting node, a
 * cut-off link and the cycle that originated the detection.
 */
public class DetectionData {

    private final Router detectingRouter;
    private final Link cutOffLink;
    private final Path cycle;
    private final Attribute initialAttribute;
    private final boolean falsePositive;

    public DetectionData(Router detectingRouter, Link cutOffLink, Path cycle, Attribute initialAttribute,
                         boolean falsePositive) {

        this.detectingRouter = detectingRouter;
        this.cutOffLink = cutOffLink;
        this.cycle = cycle;
        this.initialAttribute = initialAttribute;
        this.falsePositive = falsePositive;
    }

    public Router getDetectingRouter() {
        return detectingRouter;
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
        return "{" + detectingRouter + ", " + cutOffLink + ", " + cycle + '}';
    }
}
