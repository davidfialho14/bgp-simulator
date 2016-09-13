package policies.siblings;

import core.Attribute;
import core.topology.Label;
import core.topology.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static policies.siblings.ProviderAttribute.provider;

/**
 * Implements the provider label.
 */
public class ProviderLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<SiblingAttribute.Type, Attribute> extendTable =
            new EnumMap<>(SiblingAttribute.Type.class);

    static {
        extendTable.put(SiblingAttribute.Type.SELF, provider(0));
        extendTable.put(SiblingAttribute.Type.CUSTOMER, provider(0));
        extendTable.put(SiblingAttribute.Type.PEER, provider(0));
        extendTable.put(SiblingAttribute.Type.PROVIDER, provider(0));
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        SiblingAttribute siblingAttribute = (SiblingAttribute) attribute;
        return extendTable.get(siblingAttribute.getType());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProviderLabel;
    }

    @Override
    public int hashCode() {
        return 33;  // must be different from all Sibling labels
    }

    @Override
    public String toString() {
        return "P";
    }
}