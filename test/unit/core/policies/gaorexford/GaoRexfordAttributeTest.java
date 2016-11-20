package core.policies.gaorexford;

import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.CustomerAttribute.customer;
import static core.policies.gaorexford.PeerAttribute.peer;
import static core.policies.gaorexford.ProviderAttribute.provider;
import static core.policies.gaorexford.SelfAttribute.self;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GaoRexfordAttributeTest {

    @Test
    public void compareTo_CustomerToCustomer_Equal() throws Exception {
        assertThat(customer().compareTo(customer()), is(equalTo(0)));
    }

    @Test
    public void compareTo_PeerToPeer_Equal() throws Exception {
        assertThat(peer().compareTo(peer()), is(equalTo(0)));
    }

    @Test
    public void compareTo_ProviderToProvider_Equal() throws Exception {
        assertThat(provider().compareTo(provider()), is(equalTo(0)));
    }

    @Test
    public void compareTo_CustomerToPeer_Less() throws Exception {
        assertThat(customer().compareTo(peer()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerToCustomer_Greater() throws Exception {
        assertThat(customer().compareTo(provider()), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToCustomer_Greater() throws Exception {
        assertThat(provider().compareTo(customer()), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerToProvider_Less() throws Exception {
        assertThat(peer().compareTo(provider()), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToPeer_Greater() throws Exception {
        assertThat(provider().compareTo(peer()), is(greaterThan(0)));
    }

    @Test
    public void compareTo_CustomerToInvalid_Less() throws Exception {
        assertThat(customer().compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerToInvalid_Less() throws Exception {
        assertThat(peer().compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToInvalid_Less() throws Exception {
        assertThat(provider().compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToSelf_Equal() throws Exception {
        assertThat(self().compareTo(self()), is(equalTo(0)));
    }

    @Test
    public void compareTo_SelfToCustomer_Less() throws Exception {
        assertThat(self().compareTo(customer()), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToPeer_Less() throws Exception {
        assertThat(self().compareTo(peer()), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToProvider_Less() throws Exception {
        assertThat(self().compareTo(provider()), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToInvalid_Less() throws Exception {
        assertThat(self().compareTo(invalidAttr()), is(lessThan(0)));
    }

}