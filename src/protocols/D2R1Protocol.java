package protocols;

import core.Protocol;
import core.network.Link;
import core.Route;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol extends Reaction1 implements Protocol, Detection2 {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return Detection2.isOscillation(link.getSource(),
                learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute);
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
