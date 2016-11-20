package core.policies.siblings;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.policies.siblings.SiblingAttribute.Type.*;

/**
 * Implements the customer label.
 */
public class CustomerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<SiblingAttribute.Type, Attribute> extendTable =
            new EnumMap<>(SiblingAttribute.Type.class);

    static {
        extendTable.put(SELF, CustomerAttribute.customer(0));
        extendTable.put(CUSTOMER, CustomerAttribute.customer(0));
        extendTable.put(PEER, InvalidAttribute.invalidAttr());
        extendTable.put(PROVIDER, InvalidAttribute.invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

        SiblingAttribute siblingAttribute = (SiblingAttribute) attribute;
        return extendTable.get(siblingAttribute.getType());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CustomerLabel;
    }

    @Override
    public int hashCode() {
        return 31;  // must be different from all Sibling labels
    }

    @Override
    public String toString() {
        return "C";
    }
}
