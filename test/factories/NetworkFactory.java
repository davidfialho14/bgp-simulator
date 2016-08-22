package factories;

import core.topology.Network;

/**
 * Interface that all core.topology factories should implement.
 */
public interface NetworkFactory {

    /**
     * Creates a core.topology instance initialized according to the core.topology ID given.
     *
     * @param networkId id of the core.topology to create
     * @return core.topology created.
     */
    Network network(int networkId);

}
