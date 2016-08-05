package policies.gaorexford;

import org.junit.Before;
import org.junit.Test;
import core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static policies.InvalidAttribute.invalid;
import static policies.gaorexford.CustomerAttribute.customer;
import static policies.gaorexford.PeerAttribute.peer;
import static policies.gaorexford.ProviderAttribute.provider;
import static policies.gaorexford.SelfAttribute.self;

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
        assertThat(label.extend(null, invalid()).isInvalid(), is(true));
    }

}