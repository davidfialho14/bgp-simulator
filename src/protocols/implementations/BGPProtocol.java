package protocols.implementations;

import network.Link;
import network.Node;
import policies.Attribute;
import protocols.Protocol;
import simulation.PathAttribute;
import simulation.Route;

public class BGPProtocol implements Protocol {

    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
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

    @Override
    public void reset() {

    }

}
