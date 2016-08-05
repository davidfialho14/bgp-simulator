package policies.gaorexford;

import network.Link;
import core.Attribute;
import core.Label;

import static core.InvalidAttribute.invalid;
import static policies.gaorexford.PeerAttribute.peer;

/**
 * Implements the peer label.
 */
public class PeerLabel implements Label {

    /**
     * Table gives the result of extending each type of attribute.
     */
    private static final Attribute[] extendTable = {
            peer(), peer(),   invalid(),  invalid()
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute.isInvalid()) return invalid();

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
