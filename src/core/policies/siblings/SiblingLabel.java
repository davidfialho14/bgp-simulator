package core.policies.siblings;


import core.Attribute;
import core.Label;
import core.Link;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.GRAttribute.self;
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

        SiblingsAttribute siblingAttribute = (SiblingsAttribute) attribute;

        if (siblingAttribute.getBaseAttribute() == self()) {
            return customer(1);
        }

        return new SiblingsAttribute(siblingAttribute.getBaseAttribute(),
                siblingAttribute.getHopCount() + 1);
    }

    @Override
    public String toString() {
        return "S";
    }
}