package policies.roc;

import core.Attribute;

public abstract class RoCAttribute extends Attribute {

    abstract Type getType();

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute.isInvalid()) return -1;

        RoCAttribute other = (RoCAttribute) attribute;
        return this.getType().compareTo(other.getType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoCAttribute that = (RoCAttribute) o;

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
