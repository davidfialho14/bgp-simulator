package core.policies.siblings;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.siblings.CustomerAttribute.customer;
import static core.policies.siblings.PeerAttribute.peer;
import static core.policies.siblings.ProviderAttribute.provider;
import static core.policies.siblings.SelfAttribute.self;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PeerLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = new PeerLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsPeerWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(peer(0)));
    }

    @Test
    public void extend_CustomerWith2HopsAttribute_ReturnsPeerWith0HopsAttribute() throws Exception {
        assertThat(label.extend(null, customer(2)), is(peer(0)));
    }

    @Test
    public void extend_PeerWith2HopsAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, peer(2)), is(invalidAttr()));
    }

    @Test
    public void extend_ProviderWith2HopsAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, provider(2)), is(invalidAttr()));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalidAttr()), is(invalidAttr()));
    }

}