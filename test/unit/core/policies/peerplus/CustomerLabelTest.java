package core.policies.peerplus;


import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.peerplus.PeerPlusAttribute.*;
import static core.policies.peerplus.PeerPlusLab.customerLabel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CustomerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = customerLabel();
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