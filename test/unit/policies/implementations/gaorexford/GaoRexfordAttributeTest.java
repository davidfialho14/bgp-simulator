package policies.implementations.gaorexford;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static policies.InvalidAttribute.invalid;
import static policies.implementations.gaorexford.CustomerAttribute.customer;
import static policies.implementations.gaorexford.PeerAttribute.peer;
import static policies.implementations.gaorexford.ProviderAttribute.provider;

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
        assertThat(customer().compareTo(invalid()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerToInvalid_Less() throws Exception {
        assertThat(peer().compareTo(invalid()), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToInvalid_Less() throws Exception {
        assertThat(provider().compareTo(invalid()), is(lessThan(0)));
    }

}