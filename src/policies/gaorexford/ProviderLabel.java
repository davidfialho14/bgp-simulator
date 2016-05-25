package policies.gaorexford;

import network.Link;
import policies.Attribute;
import policies.Label;

import static policies.InvalidAttribute.invalid;
import static policies.gaorexford.ProviderAttribute.provider;

/**
 * Implements the provider label.
 */
public class ProviderLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            provider(), provider(),   provider(),  provider()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) return invalid();

        GaoRexfordAttribute gaoRexfordAttribute = (GaoRexfordAttribute) attribute;
        return extendTable[gaoRexfordAttribute.getType().ordinal()];
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProviderLabel;
    }

    @Override
    public int hashCode() {
        return 33;  // must be different from all Gao Rexford labels
    }

    @Override
    public String toString() {
        return "P";
    }
}