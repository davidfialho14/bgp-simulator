package simulation.implementations.policies.gaorexford;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GaoRexfordAttributeTest {

    @Test
    public void compareTo_CustomerToCustomer_Equal() throws Exception {
        GaoRexfordAttribute customer1 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        GaoRexfordAttribute customer2 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);

        assertThat(customer1.compareTo(customer2), is(equalTo(0)));
    }

    @Test
    public void compareTo_PeerToPeer_Equal() throws Exception {
        GaoRexfordAttribute peer1 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);
        GaoRexfordAttribute peer2 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        assertThat(peer1.compareTo(peer2), is(equalTo(0)));
    }

    @Test
    public void compareTo_ProviderToProvider_Equal() throws Exception {
        GaoRexfordAttribute provider1 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);
        GaoRexfordAttribute provider2 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        assertThat(provider1.compareTo(provider2), is(equalTo(0)));
    }

    @Test
    public void compareTo_InvalidToInvalid_Equal() throws Exception {
        GaoRexfordAttribute invalid1 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);
        GaoRexfordAttribute invalid2 = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        assertThat(invalid1.compareTo(invalid2), is(equalTo(0)));
    }

    @Test
    public void compareTo_CustomerToPeer_Less() throws Exception {
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        assertThat(customer.compareTo(peer), is(lessThan(0)));
    }

    @Test
    public void compareTo_PeerToCustomer_Greater() throws Exception {
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);

        assertThat(peer.compareTo(customer), is(greaterThan(0)));
    }

    @Test
    public void compareTo_CustomerToProvider_Less() throws Exception {
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        assertThat(customer.compareTo(provider), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToCustomer_Greater() throws Exception {
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);

        assertThat(provider.compareTo(customer), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerToProvider_Less() throws Exception {
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        assertThat(peer.compareTo(provider), is(lessThan(0)));
    }

    @Test
    public void compareTo_ProviderToPeer_Greater() throws Exception {
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        assertThat(provider.compareTo(peer), is(greaterThan(0)));
    }

    @Test
    public void compareTo_CustomerToInvalid_Less() throws Exception {
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        assertThat(customer.compareTo(invalid), is(lessThan(0)));
    }

    @Test
    public void compareTo_InvalidToCustomer_Greater() throws Exception {
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);
        GaoRexfordAttribute customer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.CUSTOMER);

        assertThat(invalid.compareTo(customer), is(greaterThan(0)));
    }

    @Test
    public void compareTo_PeerToInvalid_Less() throws Exception {
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        assertThat(peer.compareTo(invalid), is(lessThan(0)));
    }

    @Test
    public void compareTo_InvalidToPeer_Greater() throws Exception {
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);
        GaoRexfordAttribute peer = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PEER);

        assertThat(invalid.compareTo(peer), is(greaterThan(0)));
    }

    @Test
    public void compareTo_ProviderToInvalid_Less() throws Exception {
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);

        assertThat(provider.compareTo(invalid), is(lessThan(0)));
    }

    @Test
    public void compareTo_InvalidToProvider_Greater() throws Exception {
        GaoRexfordAttribute invalid = new GaoRexfordAttribute(GaoRexfordAttribute.Type.INVALID);
        GaoRexfordAttribute provider = new GaoRexfordAttribute(GaoRexfordAttribute.Type.PROVIDER);

        assertThat(invalid.compareTo(provider), is(greaterThan(0)));
    }

}