package v2.core.protocols;

import v2.core.Link;
import v2.core.Route;


/**
 * Implementation of the simple detection which requires only one conditions:
 *  - the attribute of the learned route must be preferred to the attribute of the alternative route
 */
public class SimpleDetection implements Detection {

    /**
     * Checks if the attribute of the learned route is preferred to the attribute of the alternative route.
     *
     * @param link             ignored
     * @param learnedRoute     new learned route.
     * @param alternativeRoute most preferred route learned from other neighbor.
     * @return true if the attribute of the learned route is preferred to the attribute of the alternative route.
     */
    @Override
    public boolean isPolicyConflict(Link link, Route learnedRoute, Route alternativeRoute) {
        return learnedRoute.getAttribute().compareTo(alternativeRoute.getAttribute()) < 0;
    }

    @Override
    public String toString() {
        return "D1";
    }

}