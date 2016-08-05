package wrappers;

import core.Label;
import policies.gaorexford.CustomerLabel;
import policies.gaorexford.PeerLabel;
import policies.gaorexford.ProviderLabel;
import policies.Path;
import wrappers.routetable.OutLinkElement;
import wrappers.routetable.RouteElement;

import static policies.gaorexford.CustomerAttribute.customer;
import static policies.gaorexford.PeerAttribute.peer;
import static policies.gaorexford.ProviderAttribute.provider;
import static policies.gaorexford.SelfAttribute.self;
import static wrappers.PathWrapper.path;
import static wrappers.routetable.OutLinkElement.outLink;
import static wrappers.routetable.RouteElement.route;

/**
 * Implements wrapper methods to create attributes, routes or labels for the Gao-Rexford policy.
 */
public interface GaoRexfordWrapper {

    static Label customerLabel() {
        return new CustomerLabel();
    }

    static Label peerLabel() {
        return new PeerLabel();
    }

    static Label providerLabel() {
        return new ProviderLabel();
    }

    /**
     * Wrapper around the usual out-link element wrapper for customer labels.
     *
     * @return a new link instance with a customer label.
     */
    static OutLinkElement customerLink(int srcId, int destId) {
        return outLink(srcId, destId, customerLabel());
    }

    /**
     * Wrapper around the usual out-link element wrapper for peer labels.
     *
     * @return a new link instance with a peer label.
     */
    static OutLinkElement peerLink(int srcId, int destId) {
        return outLink(srcId, destId, peerLabel());
    }

    /**
     * Wrapper around the usual out-link element wrapper for provider labels.
     *
     * @return a new link instance with a provider label.
     */
    static OutLinkElement providerLink(int srcId, int destId) {
        return outLink(srcId, destId, providerLabel());
    }

    /**
     * Wrapper around the usual route wrapper for self attribute.
     *
     * @return a new route instance with a self attribute and path.
     */
    static RouteElement selfRoute() {
        return route(self(), path());
    }

    /**
     * Wrapper around the usual route wrapper for customer attribute.
     *
     * @return a new route instance with a customer attribute and path.
     */
    static RouteElement customerRoute(Path path) {
        return route(customer(), path);
    }

    /**
     * Wrapper around the usual route wrapper for peer attribute.
     *
     * @return a new route instance with a peer attribute and path.
     */
    static RouteElement peerRoute(Path path) {
        return route(peer(), path);
    }

    /**
     * Wrapper around the usual route wrapper for provider attribute.
     *
     * @return a new route instance with a provider attribute and path.
     */
    static RouteElement providerRoute(Path path) {
        return route(provider(), path);
    }

}
