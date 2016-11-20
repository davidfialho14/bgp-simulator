package v2.core.policies.gaorexford;

import org.junit.Before;
import org.junit.Test;
import v2.core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.gaorexford.CustomerAttribute.customer;
import static v2.core.policies.gaorexford.PeerAttribute.peer;
import static v2.core.policies.gaorexford.ProviderAttribute.provider;
import static v2.core.policies.gaorexford.SelfAttribute.self;

public class ProviderLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new ProviderLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsSelfAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(provider()));
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