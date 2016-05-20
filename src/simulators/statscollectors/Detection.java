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

    public Detection(Node detectingNode, Link cutoffLink, Path cycle) {
        this.detectingNode = detectingNode;
        this.cutoffLink = cutoffLink;
        this.cycle = cycle;
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

    @Override
    public String toString() {
        return "{" + detectingNode + ", " + cutoffLink + ", " + cycle + '}';
    }
}
