package stubs;

import core.Attribute;
import core.topology.Label;
import core.topology.ConnectedNode;
import core.topology.Policy;
import io.networkreaders.exceptions.InvalidPolicyTagException;

public class StubPolicy implements Policy {
    /**
     * Creates a self attribute corresponding to the given node.
     *
     * @param node node to create self attribute for.
     * @return instance of a self attribute implementation.
     */
    @Override
    public Attribute createSelf(ConnectedNode node) {
        return new StubAttribute(0);
    }

    /**
     * Creates a label for this policy based on the string tag given.
     *
     * @param tag tag that defines the label to be created.
     * @return label instance according to the string tag.
     */
    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        return new StubLabel();
    }
}
