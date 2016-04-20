package protocols.implementations;

import network.Link;
import policies.Attribute;
import protocols.Protocol;
import simulation.PathAttribute;
import simulation.Route;

/**
 * Implements the detection D1 and the reaction R1.
 */
public class D1R1Protocol extends Reaction1 implements Protocol, Detection1 {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        return Detection1.isOscillation(attribute, exclRoute);
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        setParameters(link, learnedRoute);
    }

}
