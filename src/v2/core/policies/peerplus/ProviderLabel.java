package v2.core.policies.peerplus;


import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import java.util.EnumMap;
import java.util.Map;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.peerplus.PeerPlusAbstractAttribute.Type.*;
import static v2.core.policies.peerplus.ProviderAttribute.provider;

/**
 * Implements the provider label.
 */
public class ProviderLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<PeerPlusAbstractAttribute.Type, Attribute> extendTable =
            new EnumMap<>(PeerPlusAbstractAttribute.Type.class);

    static {
        extendTable.put(SELF, provider());
        extendTable.put(PEER_PLUS, provider());
        extendTable.put(CUSTOMER, provider());
        extendTable.put(PEER, provider());
        extendTable.put(PROVIDER, provider());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        PeerPlusAbstractAttribute rocAttribute = (PeerPlusAbstractAttribute) attribute;
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