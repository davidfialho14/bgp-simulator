package policies.implementations.gaorexford;

import policies.Attribute;
import policies.AttributeFactory;
import network.Node;

import static policies.InvalidAttribute.invalid;
import static policies.implementations.gaorexford.CustomerAttribute.customer;

public class GaoRexfordAttributeFactory implements AttributeFactory {

    @Override
    public Attribute createSelf(Node node) {
        // TODO consider a special self attribute
        return customer();
    }

    @Override
    public Attribute createInvalid() {
        return invalid();
    }

}
