package core.policies.gaorexford;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

/**
 * Implements the provider label.
 */
public class ProviderLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            ProviderAttribute.provider(), ProviderAttribute.provider(),   ProviderAttribute.provider(),  ProviderAttribute.provider()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

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