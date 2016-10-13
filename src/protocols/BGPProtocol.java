package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.Link;
import core.topology.Node;

/**
 * Implements the common BGP protocol. This means that the extend operation will be the common BGP operation without
 * any changes and any other operation will be ignored or do absolutely nothing.
 */
public class BGPProtocol implements Protocol {

    /**
     * Performs the common BGP extend. This means it simply extends the attribute using the link extend operation.
     *
     * @param destination the destination node.
     * @param link link to extend the attribute.
     * @param attribute attribute to be extended.
     * @return the extended attribute.
     */
    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
        return link.extend(attribute);
    }

    /**
     * Always returns false since the protocol does not detect oscillations.
     *
     * @return false
     */
    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute) {
        return false;
    }

    /**
     * Does nothing.
     */
    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        // ignore
    }

    /**
     * Does nothing.
     */
    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "BGP";
    }

}
