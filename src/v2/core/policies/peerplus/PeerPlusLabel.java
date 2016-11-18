package v2.core.policies.peerplus;

import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import java.util.EnumMap;
import java.util.Map;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.peerplus.PeerPlusAbstractAttribute.Type.*;
import static v2.core.policies.peerplus.PeerPlusAttribute.peerplus;

/**
 * Implements the peer+ label.
 */
public class PeerPlusLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<PeerPlusAbstractAttribute.Type, Attribute> extendTable =
            new EnumMap<>(PeerPlusAbstractAttribute.Type.class);

    static {
        extendTable.put(SELF, peerplus());
        extendTable.put(PEER_PLUS, peerplus());
        extendTable.put(CUSTOMER, peerplus());
        extendTable.put(PEER, invalidAttr());
        extendTable.put(PROVIDER, invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        PeerPlusAbstractAttribute rocAttribute = (PeerPlusAbstractAttribute) attribute;
        return extendTable.get(rocAttribute.getType());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PeerPlusLabel;
    }

    @Override
    public int hashCode() {
        return 34;  // must be different from all RoC labels
    }

    @Override
    public String toString() {
        return "R+";
    }
}
