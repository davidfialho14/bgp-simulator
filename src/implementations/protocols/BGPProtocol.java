package implementations.protocols;

import network.*;

public class BGPProtocol implements Protocol {

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        // TODO - implement BGPProtocol.extend
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        // TODO - implement BGPProtocol.isOscillation
        throw new UnsupportedOperationException();
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        // TODO - implement BGPProtocol.setParameters
        throw new UnsupportedOperationException();
    }

}
