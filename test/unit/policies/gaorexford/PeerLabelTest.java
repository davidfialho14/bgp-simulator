package policies.gaorexford;

import org.junit.Before;
import org.junit.Test;
import core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static core.InvalidAttribute.invalidAttr;
import static policies.gaorexford.CustomerAttribute.customer;
import static policies.gaorexford.PeerAttribute.peer;
import static policies.gaorexford.ProviderAttribute.provider;
import static policies.gaorexford.SelfAttribute.self;

public class PeerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new PeerLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsSelfAttribute() throws Exception {
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