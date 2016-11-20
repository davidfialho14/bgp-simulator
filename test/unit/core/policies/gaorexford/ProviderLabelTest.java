package core.policies.gaorexford;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.CustomerAttribute.customer;
import static core.policies.gaorexford.PeerAttribute.peer;
import static core.policies.gaorexford.ProviderAttribute.provider;
import static core.policies.gaorexford.SelfAttribute.self;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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