package core.policies.siblings;

import core.Attribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.siblings.ProviderAttribute.provider;
import static core.policies.siblings.SiblingAttribute.Type.*;

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
        extendTable.put(SELF, provider(0));
        extendTable.put(CUSTOMER, provider(0));
        extendTable.put(PEER, provider(0));
        extendTable.put(PROVIDER, provider(0));
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