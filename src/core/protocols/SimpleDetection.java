package core.protocols;

import core.Link;
import core.Route;


/**
 * Implementation of the simple detection which requires only one conditions:
 *  - the attribute of the learned route must be preferred to the attribute of the alternative route
 *
 *  Singleton Class!
 */
public class SimpleDetection implements Detection {

    private static final SimpleDetection DETECTION = new SimpleDetection();

    private SimpleDetection() {} // use factory method

    /**
     * Returns a simple detection instance.
     *
     * @return a simple detection instance.
     */
    public static SimpleDetection simpleDetection() {
        return DETECTION;
    }

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
