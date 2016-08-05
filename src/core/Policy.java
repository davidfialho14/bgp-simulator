package core;

import io.InvalidTagException;
import network.Node;
import policies.Attribute;

/**
 * The policy interface provides the necessary methods to define a routing policy.
 */
public interface Policy {

    /**
     * Creates a self attribute corresponding to the given node.
     * @param node node to create self attribute for.
     * @return instance of a self attribute implementation.
     */
    Attribute createSelf(Node node);

    /**
     * Creates a label for this policy based on the string tag given.
     * @param tag tag that defines the label to be created.
     * @return label instance according to the string tag.
     */
    Label createLabel(String tag) throws InvalidTagException;
}
