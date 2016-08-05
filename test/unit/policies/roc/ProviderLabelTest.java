package policies.roc;

import org.junit.Before;
import org.junit.Test;
import core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static policies.InvalidAttribute.invalid;
import static policies.roc.CustomerAttribute.customer;
import static policies.roc.PeerAttribute.peer;
import static policies.roc.ProviderAttribute.provider;
import static policies.roc.SelfAttribute.self;
import static policies.roc.PeerPlusAttribute.peerplus;

public class ProviderLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new ProviderLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsProviderAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(provider()));
    }

    @Test
    public void extend_PeerPlusAttribute_ReturnsProviderAttribute() throws Exception {
        assertThat(label.extend(null, peerplus()), is(provider()));
    }

    @Test
    public void extend_CustomerAttribute_ReturnsProviderAttribute() throws Exception {
        assertThat(label.extend(null, customer()), is(provider()));
    }

    @Test
    public void extend_PeerAttribute_ReturnsProviderAttribute() throws Exception {
        assertThat(label.extend(null, peer()), is(provider()));
    }

    @Test
    public void extend_ProviderAttribute_ReturnsProviderAttribute() throws Exception {
        assertThat(label.extend(null, provider()), is(provider()));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalid()).isInvalid(), is(true));
    }

}