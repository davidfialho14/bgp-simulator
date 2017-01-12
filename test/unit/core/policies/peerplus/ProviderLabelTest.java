package core.policies.peerplus;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.peerplus.PeerPlusAttribute.*;
import static core.policies.peerplus.PeerPlusLab.providerLabel;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProviderLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = providerLabel();
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
        assertThat(label.extend(null, invalidAttr()), is(invalidAttr()));
    }

}