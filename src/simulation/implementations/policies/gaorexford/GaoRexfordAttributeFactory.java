package simulation.implementations.policies.gaorexford;

import simulation.Attribute;
import simulation.AttributeFactory;
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
