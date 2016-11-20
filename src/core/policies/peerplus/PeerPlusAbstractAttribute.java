package core.policies.peerplus;


import core.Attribute;
import core.InvalidAttribute;

public abstract class PeerPlusAbstractAttribute implements Attribute {

    abstract Type getType();

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == InvalidAttribute.invalidAttr()) return -1;

        PeerPlusAbstractAttribute other = (PeerPlusAbstractAttribute) attribute;
        return this.getType().compareTo(other.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PeerPlusAbstractAttribute that = (PeerPlusAbstractAttribute) o;

        return getType().equals(that.getType());

    }

    @Override
    public int hashCode() {
        return getType() != null ? getType().hashCode() : 0;
    }

    /**
     * This enum models the type of the attributes. They must be defined in order from the most preferred attribute
     * to the less preferred one.
     */
    protected enum Type {
        SELF, PEER_PLUS, CUSTOMER, PEER, PROVIDER
    }

}
