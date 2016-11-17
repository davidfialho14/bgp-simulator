package policies.roc;

import core.topology.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static policies.roc.CustomerAttribute.customer;
import static policies.roc.PeerAttribute.peer;
import static policies.roc.PeerPlusAttribute.peerplus;
import static policies.roc.ProviderAttribute.provider;
import static policies.roc.SelfAttribute.self;


public class CustomerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new CustomerLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsCustomerAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(customer()));
    }

    @Test
    public void extend_PeerPlusAttribute_ReturnsCustomerAttribute() throws Exception {
        assertThat(label.extend(null, peerplus()), is(customer()));
    }

    @Test
    public void extend_CustomerAttribute_ReturnsCustomerAttribute() throws Exception {
        assertThat(label.extend(null, customer()), is(customer()));
    }

    @Test
    public void extend_PeerAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, peer()), is(invalidAttr()));
    }

    @Test
    public void extend_ProviderAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, provider()), is(invalidAttr()));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalidAttr()), is(invalidAttr()));
    }

}