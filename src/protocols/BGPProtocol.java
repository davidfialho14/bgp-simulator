package protocols;

import core.Protocol;
import core.network.Link;
import core.network.Node;
import core.Attribute;
import core.Route;

public class BGPProtocol implements Protocol {

    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
        return link.extend(attribute);
    }

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return false;
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        // ignore
    }

    @Override
    public void reset() {

    }

}
