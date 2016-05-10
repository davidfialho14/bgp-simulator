package protocols;

import network.Link;
import simulation.Route;

/**
 * Implements the detection D1 and the reaction R1.
 */
public class D1R1Protocol extends Reaction1 implements Protocol, Detection1 {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return Detection1.isOscillation(learnedRoute.getAttribute(), exclRoute);
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        setParameters(link, learnedRoute);
    }

    @Override
    public void reset() {
        destinationCutLinks.clear();
    }

}
