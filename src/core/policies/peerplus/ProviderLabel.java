package core.policies.peerplus;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

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
        extendTable.put(PeerPlusAbstractAttribute.Type.SELF, ProviderAttribute.provider());
        extendTable.put(PeerPlusAbstractAttribute.Type.PEER_PLUS, ProviderAttribute.provider());
        extendTable.put(PeerPlusAbstractAttribute.Type.CUSTOMER, ProviderAttribute.provider());
        extendTable.put(PeerPlusAbstractAttribute.Type.PEER, ProviderAttribute.provider());
        extendTable.put(PeerPlusAbstractAttribute.Type.PROVIDER, ProviderAttribute.provider());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

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