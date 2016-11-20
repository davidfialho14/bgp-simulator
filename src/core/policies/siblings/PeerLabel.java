package core.policies.siblings;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.policies.siblings.PeerAttribute.peer;
import static core.policies.siblings.SiblingAttribute.Type.*;

/**
 * Implements the peer label.
 */
public class PeerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Map<SiblingAttribute.Type, Attribute> extendTable =
            new EnumMap<>(SiblingAttribute.Type.class);

    static {
        extendTable.put(SELF, peer(0));
        extendTable.put(CUSTOMER, peer(0));
        extendTable.put(PEER, InvalidAttribute.invalidAttr());
        extendTable.put(PROVIDER, InvalidAttribute.invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

        SiblingAttribute siblingAttribute = (SiblingAttribute) attribute;
        return extendTable.get(siblingAttribute.getType());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PeerLabel;
    }

    @Override
    public int hashCode() {
        return 32;  // must be different from all Sibling labels
    }

    @Override
    public String toString() {
        return "R";
    }
}
