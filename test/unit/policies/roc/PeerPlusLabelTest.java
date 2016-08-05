package policies.roc;

import org.junit.Before;
import org.junit.Test;
import core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static core.InvalidAttribute.invalid;
import static policies.roc.CustomerAttribute.customer;
import static policies.roc.PeerAttribute.peer;
import static policies.roc.ProviderAttribute.provider;
import static policies.roc.SelfAttribute.self;
import static policies.roc.PeerPlusAttribute.peerplus;

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
        assertThat(label.extend(null, peer()).isInvalid(), is(true));
    }

    @Test
    public void extend_ProviderAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, provider()).isInvalid(), is(true));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalid()).isInvalid(), is(true));
    }

}