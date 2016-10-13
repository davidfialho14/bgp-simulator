package core;

import core.topology.Link;
import core.topology.Node;


/**
 * Base interface for any protocol implementation. All protocol implementations must implement this interface.
 */
public interface Protocol {

    /**
     * Extends the attribute using the given link, while applying some modifications to the import properties
     * according to the protocol implemented.
     *
     * @param destination the destination node
     * @param link link to extend the attribute.
     * @param attribute attribute to be extended.
     * @return extended attribute (new instance).
     */
    Attribute extend(Node destination, Link link, Attribute attribute);

    /**
     * Checks if the conditions to detect a policy-based oscillation is verified. This should called every time a new
     * route containing loop is learned.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor (not the destination node of the link)
     * @return true if the detection conditions are verified and false otherwise.
     */
    boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute);

    /**
     * Sets the parameters used by the extend operation. This adds more flexibility to configure the extend operation.
     * Some of the following parameters might not be used.
     *
     * @param link link from which the route was learned.
     * @param learnedRoute route learned by the node.
     * @param exclRoute route preferred excluding the node from which the route was learned.
     */
    void setParameters(Link link, Route learnedRoute, Route exclRoute);

    void reset();
}
