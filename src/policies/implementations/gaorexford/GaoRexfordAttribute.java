package policies.implementations.gaorexford;

import policies.Attribute;

public abstract class GaoRexfordAttribute extends Attribute {

    /**
     * This enum models the type of the attributes.
     * Customer is the lowest which means the most preferred attribute, followed by the peer attribute, which
     * is followed by the provider attribute.
     */
    protected enum Type {
        CUSTOMER, PEER, PROVIDER
    }

    abstract Type getType();

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute.isInvalid()) return -1;

        GaoRexfordAttribute other = (GaoRexfordAttribute) attribute;
        return this.getType().compareTo(other.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GaoRexfordAttribute that = (GaoRexfordAttribute) o;

        return getType().equals(that.getType());

    }

    @Override
    public int hashCode() {
        return getType() != null ? getType().hashCode() : 0;
    }

}
