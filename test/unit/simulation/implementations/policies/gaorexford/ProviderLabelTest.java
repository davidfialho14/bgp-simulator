package simulation.implementations.policies.gaorexford;

import simulation.Attribute;
import simulation.Label;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProviderLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new ProviderLabel();
    }

    @Test
    public void extend_CustomerAttribute_ReturnsProviderAttribute() throws Exception {
        Attribute customerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        Attribute providerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        Attribute extendedAttribute = label.extend(null, customerAttribute);

        assertThat(extendedAttribute, is(providerAttribute));
    }

    @Test
    public void extend_PeerAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute peerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);
        Attribute providerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        Attribute extendedAttribute = label.extend(null, peerAttribute);

        assertThat(extendedAttribute, is(providerAttribute));
    }

    @Test
    public void extend_ProviderAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute providerAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        Attribute extendedAttribute = label.extend(null, providerAttribute);

        assertThat(extendedAttribute, is(providerAttribute));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        Attribute invalidAttribute = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        Attribute extendedAttribute = label.extend(null, invalidAttribute);

        assertThat(extendedAttribute.isInvalid(), is(true));
    }

}