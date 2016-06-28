package policies.roc;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static policies.InvalidAttribute.invalid;
import static policies.roc.CustomerAttribute.customer;
import static policies.roc.PeerAttribute.peer;
import static policies.roc.PeerPlusAttribute.peerplus;
import static policies.roc.ProviderAttribute.provider;
import static policies.roc.SelfAttribute.self;

public class RoCAttributeTest {

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

    @Test
    public void compareTo_SelfToSelf_Equal() throws Exception {
        assertThat(self().compareTo(self()), is(equalTo(0)));
    }

    @Test
    public void compareTo_SelfToPeerPlus_Less() throws Exception {
        assertThat(self().compareTo(peerplus()), is(lessThan(0)));
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
        assertThat(self().compareTo(invalid()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerPlusToPeerPlus_Equal() throws Exception {
        assertThat(peerplus().compareTo(peerplus()), is(equalTo(0)));
    }

    @Test
    public void compareTo_PeerPlusToCustomer_Less() throws Exception {
        assertThat(peerplus().compareTo(customer()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerPlusToPeer_Less() throws Exception {
        assertThat(peerplus().compareTo(peer()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerPlusToProvider_Less() throws Exception {
        assertThat(peerplus().compareTo(provider()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerPlusToInvalid_Less() throws Exception {
        assertThat(peerplus().compareTo(invalid()), is(lessThan(0)));
    }

}