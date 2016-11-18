package v2.core.protocols;

import v2.core.Link;
import v2.core.Route;


/**
 * Implements a dummy detection method. This implementation never detects a policy conflict
 */
public class DummyDetection implements Detection {

    /**
     * Returns always false.
     *
     * @param link             ignored
     * @param learnedRoute     ignored
     * @param alternativeRoute ignored
     * @return always false.
     */
    @Override
    public boolean isPolicyConflict(Link link, Route learnedRoute, Route alternativeRoute) {
        return false;
    }

    @Override
    public String toString() {
        return "D0";
    }

}
