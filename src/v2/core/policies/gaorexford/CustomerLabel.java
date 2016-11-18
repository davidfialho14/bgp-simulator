package v2.core.policies.gaorexford;

import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.gaorexford.CustomerAttribute.customer;

/**
 * Implements the customer label.
 */
public class CustomerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            customer(), customer(), invalidAttr(), invalidAttr()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        GaoRexfordAttribute gaoRexfordAttribute = (GaoRexfordAttribute) attribute;
        return extendTable[gaoRexfordAttribute.getType().ordinal()];
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CustomerLabel;
    }

    @Override
    public int hashCode() {
        return 31;  // must be different from all Gao Rexford labels
    }

    @Override
    public String toString() {
        return "C";
    }
}
