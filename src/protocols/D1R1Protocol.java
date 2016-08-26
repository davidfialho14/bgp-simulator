package protocols;

import core.Protocol;
import core.Route;
import core.topology.Link;

/**
 * Implements the detection D1 and the reaction R1.
 */
public class D1R1Protocol extends Reaction1 implements Protocol {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return Detections.detection1(learnedRoute.getAttribute(), exclRoute.getAttribute());
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        setParameters(link, learnedRoute);
    }

    @Override
    public void reset() {
        destinationCutLinks.clear();
    }

    @Override
    public String toString() {
        return "D1R1";
    }
}
