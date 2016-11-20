package core.policies.peerplus;

import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.policies.peerplus.PeerPlusAbstractAttribute.Type.*;

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
        extendTable.put(SELF, PeerPlusAttribute.peerplus());
        extendTable.put(PEER_PLUS, PeerPlusAttribute.peerplus());
        extendTable.put(CUSTOMER, PeerPlusAttribute.peerplus());
        extendTable.put(PEER, InvalidAttribute.invalidAttr());
        extendTable.put(PROVIDER, InvalidAttribute.invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

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
