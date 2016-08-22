package core.topology;

import core.Attribute;
import io.networkreaders.exceptions.InvalidPolicyTagException;

/**
 * The policy interface provides the necessary methods to define a routing policy. Policies must be immutable classes
 * otherwise there might be some unexpected behaviour.
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
    Label createLabel(String tag) throws InvalidPolicyTagException;
}
