package core.policies.siblings;


import core.Attribute;
import core.Label;
import core.Link;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.siblings.SiblingsAttribute.customer;

/**
 * Implements the provider label.
 */
public enum SiblingLabel implements Label {
    INSTANCE;

    public static Label siblingLabel() {
        return INSTANCE;
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        if (attribute instanceof SelfAttribute) {
            return customer(1);
        }

        SiblingAttribute siblingAttribute = (SiblingAttribute) attribute;
        return siblingAttribute.newInstance(siblingAttribute.getHopCount() + 1);
    }

    @Override
    public String toString() {
        return "S";
    }
}