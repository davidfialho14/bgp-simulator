package policies.implementations.shortestpath;

import policies.AttributeFactory;
import policies.Label;
import policies.Policy;
import policies.exceptions.InvalidTagException;

public class ShortestPathPolicy implements Policy {

    @Override
    public AttributeFactory getAttributeFactory() {
        return new ShortestPathAttributeFactory();
    }

    @Override
    public Label createLabel(String tag) throws InvalidTagException {
        try {
            return new ShortestPathLabel(Integer.parseInt(tag));
        } catch (NumberFormatException e) {
            // tag is not an integer
            throw new InvalidTagException("the ShortestPath policy label tags must be integers", tag);
        }
    }
}
