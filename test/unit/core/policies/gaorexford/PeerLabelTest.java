package core.policies.gaorexford;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.GRAttribute.customer;
import static core.policies.gaorexford.GRLabel.peerLabel;
import static core.policies.gaorexford.GRAttribute.peer;
import static core.policies.gaorexford.GRAttribute.provider;
import static core.policies.gaorexford.GRAttribute.self;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = peerLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsPeerAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(peer()));
    }

    @Test
    public void extend_CustomerAttribute_ReturnsPeerAttribute() throws Exception {
        assertThat(label.extend(null, customer()), is(peer()));
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