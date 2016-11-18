package v2.core.policies.gaorexford;

import v2.core.Attribute;

import static v2.core.InvalidAttribute.invalidAttr;

public abstract class GaoRexfordAttribute implements Attribute {

    abstract Type getType();

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

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

    /**
     * This enum models the type of the attributes.
     * Customer is the lowest which means the most preferred attribute, followed by the peer attribute, which
     * is followed by the provider attribute.
     */
    protected enum Type {
        SELF, CUSTOMER, PEER, PROVIDER
    }

}