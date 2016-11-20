package core.policies.peerplus;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.peerplus.CustomerAttribute.customer;
import static core.policies.peerplus.PeerAttribute.peer;
import static core.policies.peerplus.PeerPlusAttribute.peerplus;
import static core.policies.peerplus.ProviderAttribute.provider;
import static core.policies.peerplus.SelfAttribute.self;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeerPlusLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new PeerPlusLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsPeerPlusAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(peerplus()));
    }

    @Test
    public void extend_PeerPlusAttribute_ReturnsPeerPlusAttribute() throws Exception {
        assertThat(label.extend(null, peerplus()), is(peerplus()));
    }

    @Test
    public void extend_CustomerAttribute_ReturnsPeerPlusAttribute() throws Exception {
        assertThat(label.extend(null, customer()), is(peerplus()));
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