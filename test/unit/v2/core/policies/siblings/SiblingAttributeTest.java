package v2.core.policies.siblings;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.policies.siblings.CustomerAttribute.customer;
import static v2.core.policies.siblings.PeerAttribute.peer;
import static v2.core.policies.siblings.ProviderAttribute.provider;
import static v2.core.policies.siblings.SelfAttribute.self;

public class SiblingAttributeTest {

    @Test
    public void compareTo_CustomerWith0HopsToCustomerWith0Hops_Equal() throws Exception {
        assertThat(customer(0).compareTo(customer(0)), is(equalTo(0)));
    }

    @Test
    public void compareTo_CustomerWith1HopsToCustomerWith0Hops_Greater() throws Exception {
        assertThat(customer(1).compareTo(customer(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_CustomerWith0HopsToPeerWith0Hops_Less() throws Exception {
        assertThat(customer(0).compareTo(peer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_CustomerWith1HopsToPeerWith0Hops_Less() throws Exception {
        assertThat(customer(1).compareTo(peer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_CustomerWith0HopsToProviderWith0Hops_Less() throws Exception {
        assertThat(customer(0).compareTo(peer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_CustomerWith1HopsToProviderWith0Hops_Less() throws Exception {
        assertThat(customer(1).compareTo(peer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerWith0HopsToPeerWith0Hops_Equal() throws Exception {
        assertThat(peer(0).compareTo(peer(0)), is(equalTo(0)));
    }

    @Test
    public void compareTo_PeerWith1HopsToPeerWith0Hops_Greater() throws Exception {
        assertThat(peer(1).compareTo(peer(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerWith0HopsToCustomerWith0Hops_Greater() throws Exception {
        assertThat(peer(0).compareTo(customer(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerWith1HopsToCustomerWith1Hops_Greater() throws Exception {
        assertThat(peer(0).compareTo(customer(1)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerWith0HopsToProviderWith0Hops_Less() throws Exception {
        assertThat(peer(0).compareTo(provider(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerWith1HopsToProviderWith0Hops_Less() throws Exception {
        assertThat(peer(1).compareTo(provider(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderWith0HopsToProviderWith0Hops_Equal() throws Exception {
        assertThat(provider(0).compareTo(provider(0)), is(equalTo(0)));
    }

    @Test
    public void compareTo_ProviderWith1HopsToProviderWith0Hops_Greater() throws Exception {
        assertThat(provider(1).compareTo(provider(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_ProviderWith0HopsToCustomerWith0Hops_Greater() throws Exception {
        assertThat(provider(0).compareTo(customer(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_ProviderWith0HopsToCustomerWith1Hops_Greater() throws Exception {
        assertThat(provider(0).compareTo(customer(1)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_ProviderWith0HopsToPeerWith0Hops_Greater() throws Exception {
        assertThat(provider(0).compareTo(peer(0)), is(greaterThan(0)));
    }

    @Test
    public void compareTo_ProviderWith0HopsToPeerWith1Hops_Greater() throws Exception {
        assertThat(provider(0).compareTo(peer(1)), is(greaterThan(0)));
    }


    // ---

    @Test
    public void compareTo_CustomerToInvalid_Less() throws Exception {
        assertThat(customer(0).compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerToInvalid_Less() throws Exception {
        assertThat(peer(0).compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToInvalid_Less() throws Exception {
        assertThat(provider(0).compareTo(invalidAttr()), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToSelf_Equal() throws Exception {
        assertThat(self().compareTo(self()), is(equalTo(0)));
    }

    @Test
    public void compareTo_SelfToCustomer_Less() throws Exception {
        assertThat(self().compareTo(customer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToPeer_Less() throws Exception {
        assertThat(self().compareTo(peer(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToProvider_Less() throws Exception {
        assertThat(self().compareTo(provider(0)), is(lessThan(0)));
    }

    @Test
    public void compareTo_SelfToInvalid_Less() throws Exception {
        assertThat(self().compareTo(invalidAttr()), is(lessThan(0)));
    }

}