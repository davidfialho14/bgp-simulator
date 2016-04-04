package policies.implementations.gaorexford;

import policies.Attribute;
import policies.Label;
import network.Link;

public abstract class GaoRexfordLabel implements Label {

    private static final GaoRexfordAttribute.Type[][] extension = {
            {GaoRexfordAttribute.Type.CUSTOMER, GaoRexfordAttribute.Type.INVALID,
                    GaoRexfordAttribute.Type.INVALID, GaoRexfordAttribute.Type.INVALID},
            {GaoRexfordAttribute.Type.PEER, GaoRexfordAttribute.Type.INVALID,
                    GaoRexfordAttribute.Type.INVALID, GaoRexfordAttribute.Type.INVALID},
            {GaoRexfordAttribute.Type.PROVIDER, GaoRexfordAttribute.Type.PROVIDER,
                    GaoRexfordAttribute.Type.PROVIDER, GaoRexfordAttribute.Type.INVALID},
    };

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        GaoRexfordAttribute gaoRexfordAttribute = (GaoRexfordAttribute) attribute;
        return new GaoRexfordAttribute(extension[getRowCode()][gaoRexfordAttribute.getType().ordinal()]);
    }

    abstract protected int getRowCode();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GaoRexfordLabel that = (GaoRexfordLabel) o;

        return getRowCode() == that.getRowCode();

    }

    @Override
    public int hashCode() {
        return getRowCode();
    }

}
