package implementations.policies.gaorexford;

import network.Attribute;
import network.Label;
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

}
