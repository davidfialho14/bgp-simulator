package policies.siblings;

import org.junit.Before;
import org.junit.Test;
import policies.Label;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static policies.InvalidAttribute.invalid;
import static policies.siblings.CustomerAttribute.customer;
import static policies.siblings.PeerAttribute.peer;
import static policies.siblings.ProviderAttribute.provider;
import static policies.siblings.SelfAttribute.self;

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
        assertThat(label.extend(null, peer(2)).isInvalid(), is(true));
    }

    @Test
    public void extend_ProviderWith2HopsAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, provider(2)).isInvalid(), is(true));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalid()).isInvalid(), is(true));
    }

}