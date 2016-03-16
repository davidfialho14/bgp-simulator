package implementations.policies.gaorexford;

import network.Attribute;
import network.Label;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new PeerLabel();
    }

    @Test
    public void extend_CustomerAttribute_ReturnsPeerAttribute() throws Exception {
        Attribute customerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        Attribute peerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        Attribute extendedAttribute = label.extend(null, customerAttribute);

        assertThat(extendedAttribute, is(peerAttribute));
    }

    @Test
    public void extend_PeerAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute peerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        Attribute extendedAttribute = label.extend(null, peerAttribute);

        assertThat(extendedAttribute.isInvalid(), is(true));
    }

    @Test
    public void extend_ProviderAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute providerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        Attribute extendedAttribute = label.extend(null, providerAttribute);

        assertThat(extendedAttribute.isInvalid(), is(true));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute invalidAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        Attribute extendedAttribute = label.extend(null, invalidAttribute);

        assertThat(extendedAttribute.isInvalid(), is(true));
    }

}