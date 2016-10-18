package protocols;

import core.NodeState;
import core.Route;
import core.topology.Link;

/**
 * Interface for a detection of a policy dispute.
 */
public interface Detection {

    /**
     * Checks if the conditions to detect a policy-based oscillation is verified. This should called every time a new
     * route containing loop is learned.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor (not the destination node of the link)
     * @param nodeState         state of the node before learning the new route.
     * @return true if the detection conditions are verified and false otherwise.
     */
    boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute, NodeState nodeState);

}
