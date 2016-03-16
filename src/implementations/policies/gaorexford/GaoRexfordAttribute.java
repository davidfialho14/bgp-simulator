package implementations.policies.gaorexford;

import network.Attribute;

public class GaoRexfordAttribute implements Attribute{

    public enum Type {
        CUSTOMER, PEER, PROVIDER, INVALID
    }

    private Type type;

    public GaoRexfordAttribute(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean isInvalid() {
        return type.equals(Type.INVALID);
    }

    @Override
    public Attribute createInvalid() {
        return new GaoRexfordAttribute(Type.INVALID);
    }

    @Override
    public int compareTo(Attribute attribute) {
        GaoRexfordAttribute other = (GaoRexfordAttribute) attribute;
        return this.type.compareTo(other.type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GaoRexfordAttribute that = (GaoRexfordAttribute) o;

        return type == that.type;

    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

}
