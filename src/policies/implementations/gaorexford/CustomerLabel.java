package policies.implementations.gaorexford;

import network.Link;
import policies.Attribute;
import policies.Label;

import static policies.InvalidAttribute.invalid;
import static policies.implementations.gaorexford.CustomerAttribute.customer;

/**
 * Implements the customer label.
 */
public class CustomerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            customer(), customer(),   invalid(),  invalid()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) return invalid();

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
