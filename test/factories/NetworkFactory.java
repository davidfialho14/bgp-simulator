package factories;

import core.network.Network;

/**
 * Interface that all core.network factories should implement.
 */
public interface NetworkFactory {

    /**
     * Creates a core.network instance initialized according to the core.network ID given.
     *
     * @param networkId id of the core.network to create
     * @return core.network created.
     */
    Network network(int networkId);

}
