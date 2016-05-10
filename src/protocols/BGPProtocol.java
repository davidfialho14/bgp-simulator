package protocols;

import network.Link;
import network.Node;
import policies.Attribute;
import simulation.Route;

public class BGPProtocol implements Protocol {

    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
        return link.extend(attribute);
    }

    @Override
    public boolean isOscillation(Link link, Route importedRoute, Route learnedRoute, Route exclRoute) {
        return false;
    }

    @Override
    public void setParameters(Link link, Route importedRoute, Route learnedRoute, Route exclRoute) {
        // ignore
    }

    @Override
    public void reset() {

    }

}
