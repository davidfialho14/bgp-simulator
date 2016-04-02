package simulation.implementations.policies.shortestpath;

import simulation.Attribute;
import simulation.AttributeFactory;
import network.Node;

public class ShortestPathAttributeFactory implements AttributeFactory {

    @Override
    public Attribute createSelf(Node node) {
        return new ShortestPathAttribute(0);
    }

    @Override
    public Attribute createInvalid() {
        return new ShortestPathAttribute();
    }

}
