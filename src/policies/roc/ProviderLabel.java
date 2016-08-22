package policies.roc;

import core.network.Link;
import core.Attribute;
import core.Label;

import java.util.EnumMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static policies.roc.ProviderAttribute.provider;

/**
 * Implements the provider label.
 */
public class ProviderLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<RoCAttribute.Type, Attribute> extendTable =
            new EnumMap<>(RoCAttribute.Type.class);

    static {
        extendTable.put(RoCAttribute.Type.SELF, provider());
        extendTable.put(RoCAttribute.Type.PEER_PLUS, provider());
        extendTable.put(RoCAttribute.Type.CUSTOMER, provider());
        extendTable.put(RoCAttribute.Type.PEER, provider());
        extendTable.put(RoCAttribute.Type.PROVIDER, provider());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        RoCAttribute rocAttribute = (RoCAttribute) attribute;
        return extendTable.get(rocAttribute.getType());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProviderLabel;
    }

    @Override
    public int hashCode() {
        return 33;  // must be different from all RoC labels
    }

    @Override
    public String toString() {
        return "P";
    }
}