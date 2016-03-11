package protocols;

import network.*;
import policies.DummyAttribute;

public class DummyProtocol implements Protocol {
    @Override
    public Attribute extend(Link link, Attribute attribute) {
        return new DummyAttribute();
    }

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        return false;
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {

    }
}
