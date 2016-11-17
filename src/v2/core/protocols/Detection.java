package v2.core.protocols;


import v2.core.Link;
import v2.core.Route;

/**
 * Interface for a detection of a policy dispute.
 */
public interface Detection {

    /**
     * Checks if the conditions to detect a policy-based oscillation is verified. This should called every
     * time a new route containing loop is learned.
     *
     * @param link              link from which the new route was learned.
     * @param learnedRoute      new learned route.
     * @param alternativeRoute  most preferred route learned from other neighbor.
     * @return true if the detection conditions are verified and false otherwise.
     */
    boolean isPolicyConflict(Link link, Route learnedRoute, Route alternativeRoute);

}
