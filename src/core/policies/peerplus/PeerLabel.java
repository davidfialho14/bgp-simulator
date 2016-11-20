package core.policies.peerplus;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.policies.peerplus.PeerAttribute.peer;

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
        extendTable.put(PeerPlusAbstractAttribute.Type.SELF, peer());
        extendTable.put(PeerPlusAbstractAttribute.Type.PEER_PLUS, peer());
        extendTable.put(PeerPlusAbstractAttribute.Type.CUSTOMER, peer());
        extendTable.put(PeerPlusAbstractAttribute.Type.PEER, InvalidAttribute.invalidAttr());
        extendTable.put(PeerPlusAbstractAttribute.Type.PROVIDER, InvalidAttribute.invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

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
