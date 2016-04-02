package simulation.implementations.protocols;

import network.*;
import simulation.Attribute;
import simulation.PathAttribute;
import simulation.Protocol;
import simulation.Route;

public class BGPProtocol implements Protocol {

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        return link.extend(attribute);
    }

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        return false;
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        // ignore
    }

}
