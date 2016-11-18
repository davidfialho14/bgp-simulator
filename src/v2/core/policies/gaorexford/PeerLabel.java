package v2.core.policies.gaorexford;


import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.gaorexford.PeerAttribute.peer;

/**
 * Implements the peer label.
 */
public class PeerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            peer(), peer(), invalidAttr(), invalidAttr()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        GaoRexfordAttribute gaoRexfordAttribute = (GaoRexfordAttribute) attribute;
        return extendTable[gaoRexfordAttribute.getType().ordinal()];
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PeerLabel;
    }

    @Override
    public int hashCode() {
        return 32;  // must be different from all Gao Rexford labels
    }

    @Override
    public String toString() {
        return "R";
    }
}