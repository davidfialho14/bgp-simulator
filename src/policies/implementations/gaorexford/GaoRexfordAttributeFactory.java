package policies.implementations.gaorexford;

import policies.Attribute;
import policies.AttributeFactory;
import network.Node;

public class GaoRexfordAttributeFactory implements AttributeFactory {

    @Override
    public Attribute createSelf(Node node) {
        // TODO consider a special self attribute
        return new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
    }

    @Override
    public Attribute createInvalid() {
        return new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);
    }

}
