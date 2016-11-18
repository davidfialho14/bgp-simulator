package v2.core.policies.peerplus;


import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import java.util.EnumMap;
import java.util.Map;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.peerplus.PeerAttribute.peer;
import static v2.core.policies.peerplus.PeerPlusAbstractAttribute.Type.*;

/**
 * Implements the peer label.
 */
public class PeerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<PeerPlusAbstractAttribute.Type, Attribute> extendTable =
            new EnumMap<>(PeerPlusAbstractAttribute.Type.class);

    static {
        extendTable.put(SELF, peer());
        extendTable.put(PEER_PLUS, peer());
        extendTable.put(CUSTOMER, peer());
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
        return o instanceof PeerLabel;
    }

    @Override
    public int hashCode() {
        return 32;  // must be different from all RoC labels
    }

    @Override
    public String toString() {
        return "R";
    }
}
