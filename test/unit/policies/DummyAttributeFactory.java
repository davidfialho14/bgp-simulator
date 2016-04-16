package policies;

import network.Node;

import static policies.InvalidAttribute.invalid;

public class DummyAttributeFactory implements AttributeFactory {

    @Override
    public Attribute createSelf(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attribute createInvalid() {
        return invalid();
    }

}
