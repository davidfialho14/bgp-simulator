package stubs;

import io.InvalidTagException;
import network.Node;
import policies.Attribute;
import policies.Label;
import policies.Policy;

public class StubPolicy implements Policy {
    /**
     * Creates a self attribute corresponding to the given node.
     *
     * @param node node to create self attribute for.
     * @return instance of a self attribute implementation.
     */
    @Override
    public Attribute createSelf(Node node) {
        return new StubAttribute(0);
    }

    /**
     * Creates a label for this policy based on the string tag given.
     *
     * @param tag tag that defines the label to be created.
     * @return label instance according to the string tag.
     */
    @Override
    public Label createLabel(String tag) throws InvalidTagException {
        return new StubLabel();
    }
}
