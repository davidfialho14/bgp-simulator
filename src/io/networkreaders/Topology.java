package io.networkreaders;

import core.Policy;
import core.network.Network;

/**
 * A topology associates a network with a routing policy. It is read only.
 */
public final class Topology {

    private final Network network;
    private final Policy policy;

    /**
     * Constructs a new topology associating the two given network and policy.
     *
     * @param network topology network
     * @param policy  topology routing policy
     */
    public Topology(Network network, Policy policy) {
        this.network = network;
        this.policy = policy;
    }

    /**
     * Returns the topology's network.
     *
     * @return the topology's network
     */
    public Network getNetwork() {
        return network;
    }

    /**
     * Returns the topology's routing policy.
     *
     * @return the topology's routing policy
     */
    public Policy getPolicy() {
        return policy;
    }
}
