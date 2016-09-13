package policies.siblings;

import core.Attribute;
import core.topology.Label;
import core.topology.Link;

import static core.InvalidAttribute.invalidAttr;
import static policies.siblings.CustomerAttribute.customer;

/**
 * Implements the provider label.
 */
public class SiblingLabel implements Label {

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();
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