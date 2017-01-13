package core.policies.siblings;

import core.Label;
import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.siblings.SiblingLabel.siblingLabel;
import static core.policies.siblings.SiblingsAttribute.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SiblingLabelTest {

    protected Label label;

    @Before
    public void setUp() throws Exception {
        label = siblingLabel();
    }

    @Test
    public void extend_SelfAttribute_ReturnsCustomerWith1HopAttribute() throws Exception {
        assertThat(label.extend(null, self()), is(customer(1)));
    }

    @Test
    public void extend_CustomerWith2HopsAttribute_ReturnsCustomerWith3HopsAttribute() throws Exception {
        assertThat(label.extend(null, customer(2)), is(customer(3)));
    }

    @Test
    public void extend_PeerWith2HopsAttribute_ReturnsPeerWith3HopsAttribute() throws Exception {
        assertThat(label.extend(null, peer(2)), is(peer(3)));
    }

    @Test
    public void extend_ProviderWith2HopsAttribute_ReturnsProviderWith3HopsAttribute() throws Exception {
        assertThat(label.extend(null, provider(2)), is(provider(3)));
    }

    @Test
    public void extend_InvalidAttribute_ReturnsInvalidAttribute() throws Exception {
        assertThat(label.extend(null, invalidAttr()), is(invalidAttr()));
    }

}