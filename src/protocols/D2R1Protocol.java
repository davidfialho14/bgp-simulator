package protocols;

import network.Link;
import policies.Attribute;
import simulation.PathAttribute;
import simulation.Route;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol extends Reaction1 implements Protocol, Detection2 {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        return Detection2.isOscillation(link.getSource(), attribute, path, exclRoute);
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        setParameters(link, learnedRoute);
    }

    @Override
    public void reset() {
        destinationCutLinks.clear();
    }

}
