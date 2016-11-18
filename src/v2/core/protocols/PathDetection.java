package v2.core.protocols;

import v2.core.Link;
import v2.core.Route;
import v2.core.Router;


/**
 * Implementation of the path detection which requires two conditions:
 *  - the attribute of the learned route must be preferred to the attribute of the alternative route (same
 *  as simple detection)
 *  - sub-path of the learned route's path, between the node and the destination, must match the path of the
 *  alternative route.
 *
 *  Singleton Class!
 */
public class PathDetection implements Detection {

    private static final PathDetection DETECTION = new PathDetection();

    private PathDetection() {} // use factory method

    /**
     * Returns a path detection instance.
     *
     * @return a path detection instance.
     */
    public static PathDetection pathDetection() {
        return DETECTION;
    }

    /**
     * Checks if the attribute of the learned route is preferred to the attribute of the alternative route
     * and the sub-path of the learned route's path, between the node and the destination, matches the path
     * of the alternative route.
     *
     * @param link             ignored
     * @param learnedRoute     new learned route.
     * @param alternativeRoute most preferred route learned from other neighbor.
     * @return true if the detection conditions are verified.
     */
    @Override
    public boolean isPolicyConflict(Link link, Route learnedRoute, Route alternativeRoute) {
        Router learningRouter = link.getSource();

        return learnedRoute.getAttribute().compareTo(alternativeRoute.getAttribute()) < 0
                && learnedRoute.getPath().getPathAfter(learningRouter).equals(alternativeRoute.getPath());
    }

    @Override
    public String toString() {
        return "D2";
    }

}
