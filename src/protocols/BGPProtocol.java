package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.Link;
import core.topology.Node;

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

    @Override
    public String toString() {
        return "BGP";
    }

}
