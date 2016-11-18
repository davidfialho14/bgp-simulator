package v2.core;


import v2.io.topologyreaders.exceptions.InvalidPolicyTagException;

/**
 * The policy interface provides the necessary methods to define a routing policy. Policy must be immutable classes
 * otherwise there might be some unexpected behaviour.
 */
public interface Policy {

    /**
     * Creates a self attribute.
     *
     * @return instance of a self attribute implementation.
     */
    Attribute createSelf();

    /**
     * Creates a label for this policy based on the string tag given.
     *
     * @param tag tag that defines the label to be created.
     * @return label instance according to the string tag.
     */
    Label createLabel(String tag) throws InvalidPolicyTagException;

}
