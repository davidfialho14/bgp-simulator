package core.policies.gaorexford;


import core.Attribute;
import core.InvalidAttribute;
import core.Label;
import core.Link;

import static core.policies.gaorexford.PeerAttribute.peer;

/**
 * Implements the peer label.
 */
public class PeerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            peer(), peer(), InvalidAttribute.invalidAttr(), InvalidAttribute.invalidAttr()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return InvalidAttribute.invalidAttr();

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
