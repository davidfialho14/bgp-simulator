package policies;

import simulation.Attribute;
import simulation.AttributeFactory;
import network.Node;

public class DummyAttributeFactory implements AttributeFactory {

    @Override
    public Attribute createSelf(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attribute createInvalid() {
        return DummyAttribute.createInvalidDummy();
    }

}
