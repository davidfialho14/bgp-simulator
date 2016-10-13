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
     * Provides the information used to detect the policy dispute. This method must be called right after a
     * detection takes place to provide information about it. This allows the reaction to identify detections
     * and to take action depending on the detection information.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor (not the destination node of the link)
     */
    void detectionInfo(Link link, Route learnedRoute, Route alternativeRoute);

    /**
     * Resets the state of the protocol. Protocols must be reset before each simulation.
     */
    void reset();
}
