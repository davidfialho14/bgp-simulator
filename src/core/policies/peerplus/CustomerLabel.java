package core.policies.peerplus;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.policies.peerplus.PeerPlusAbstractAttribute.Type.*;

/**
 * Implements the customer label.
 */
public class CustomerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<PeerPlusAbstractAttribute.Type, Attribute> extendTable =
            new EnumMap<>(PeerPlusAbstractAttribute.Type.class);

    static {
        extendTable.put(SELF, CustomerAttribute.customer());
        extendTable.put(PEER_PLUS, CustomerAttribute.customer());
        extendTable.put(CUSTOMER, CustomerAttribute.customer());
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
        return o instanceof CustomerLabel;
    }

    @Override
    public int hashCode() {
        return 31;  // must be different from all RoC labels
    }

    @Override
    public String toString() {
        return "C";
    }
}
