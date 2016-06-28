package policies.siblings;

import network.Link;
import policies.Attribute;
import policies.Label;

import static policies.InvalidAttribute.invalid;
import static policies.siblings.CustomerAttribute.customer;

/**
 * Implements the provider label.
 */
public class SiblingLabel implements Label {

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) return invalid();
        if (attribute instanceof SelfAttribute) {
            return customer(0);
        }

        SiblingAttribute siblingAttribute = (SiblingAttribute) attribute;
        return siblingAttribute.newInstance(siblingAttribute.getHopCount() + 1);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SiblingLabel;
    }

    @Override
    public int hashCode() {
        return 34;  // must be different from all Sibling labels
    }

    @Override
    public String toString() {
        return "S";
    }
}