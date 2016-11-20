package core.protocols;

import core.Link;
import core.Route;


/**
 * Implements a dummy detection method. This implementation never detects a policy conflict. Singleton class!
 */
public class DummyDetection implements Detection {

    private static final DummyDetection DETECTION = new DummyDetection();

    private DummyDetection() {} // use factory method

    /**
     * Returns a dummy detection instance.
     *
     * @return a dummy detection instance.
     */
    public static DummyDetection dummyDetection() {
        return DETECTION;
    }

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
