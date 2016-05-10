package networks.factories;

import network.Network;

/**
 * Interface that all network factories should implement.
 */
public interface NetworkFactory {

    /**
     * Creates a network instance initialized according to the network ID given.
     *
     * @param networkId id of the network to create
     * @return network created.
     */
    Network create(int networkId);

}
