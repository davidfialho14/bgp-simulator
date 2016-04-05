package policies;

import io.InvalidTagException;

public interface Policy {

    /**
     * Returns the attribute factory for this policy.
     * @return attribute factory instance according to the policy.
     */
    AttributeFactory getAttributeFactory();

    /**
     * Creates a label for this policy based on the string tag given.
     * @param tag tag that defines the label to be created.
     * @return label instance according to the string tag.
     */
    Label createLabel(String tag) throws InvalidTagException;
}
