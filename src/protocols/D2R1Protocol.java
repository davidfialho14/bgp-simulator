package protocols;

import network.Link;
import simulation.Route;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol extends Reaction1 implements Protocol, Detection2 {

    @Override
    public boolean isOscillation(Link link, Route importedRoute, Route learnedRoute, Route exclRoute) {
        return Detection2.isOscillation(link.getSource(),
                learnedRoute.getAttribute(), learnedRoute.getPath(), exclRoute);
    }

    @Override
    public void setParameters(Link link, Route importedRoute, Route learnedRoute, Route exclRoute) {
        setParameters(link, importedRoute);
    }

    @Override
    public void reset() {
        destinationCutLinks.clear();
    }

}
