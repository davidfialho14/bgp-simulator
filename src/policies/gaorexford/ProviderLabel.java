package policies.gaorexford;

import core.Attribute;
import core.topology.Label;
import core.topology.Link;

import static core.InvalidAttribute.invalidAttr;
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
        if (attribute == invalidAttr()) return invalidAttr();

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