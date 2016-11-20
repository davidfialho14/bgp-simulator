package core.policies.siblings;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

/**
 * Implements the provider label.
 */
public class SiblingLabel implements Label {

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();
        if (attribute instanceof SelfAttribute) {
            return CustomerAttribute.customer(0);
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