package v2.core.policies.siblings;

import org.junit.Before;
import org.junit.Test;
import v2.core.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.siblings.CustomerAttribute.customer;
import static v2.core.policies.siblings.PeerAttribute.peer;
import static v2.core.policies.siblings.ProviderAttribute.provider;
import static v2.core.policies.siblings.SelfAttribute.self;

public class ProviderLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new ProviderLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsProviderWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(provider(0)));
    }

    @Test
    public void extend_CustomerWith2HopsAttribute_ReturnsProviderWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, customer(2)), is(provider(0)));
    }

    @Test
    public void extend_PeerWith2HopsAttribute_ReturnsProviderWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, peer(2)), is(provider(0)));
    }

    @Test
    public void extend_ProviderWith2HopsAttribute_ReturnsProviderWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, provider(2)), is(provider(0)));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalidAttr()), is(invalidAttr()));
    }

}