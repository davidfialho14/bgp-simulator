package policies.siblings;

import core.Attribute;
import core.topology.Label;
import core.topology.Link;

import java.util.EnumMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static policies.siblings.PeerAttribute.peer;

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
        extendTable.put(SiblingAttribute.Type.SELF, peer(0));
        extendTable.put(SiblingAttribute.Type.CUSTOMER, peer(0));
        extendTable.put(SiblingAttribute.Type.PEER, invalidAttr());
        extendTable.put(SiblingAttribute.Type.PROVIDER, invalidAttr());
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

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
