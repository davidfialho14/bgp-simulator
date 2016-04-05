package policies.implementations.shortestpath;

import policies.AttributeFactory;
import policies.Label;
import policies.Policy;
import io.InvalidTagException;

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
            throw new InvalidTagException(tag, "the ShortestPath policy's label tags must be integers");
        }
    }
}
