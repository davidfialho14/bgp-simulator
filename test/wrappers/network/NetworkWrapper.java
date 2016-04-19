package wrappers.network;

import network.Network;
import network.exceptions.NodeNotFoundException;

/**
 * Implements a set of static method wrappers to improve generating a network statically in a more
 * readable way.
 */
public class NetworkWrapper {

    private NetworkWrapper() {}  // can not be instantiated outside of the class

    // ----- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Creates a network from the link elements.
     * @param links links of the network.
     * @return network instance initialized.
     */
    public static Network network(LinkElement... links) throws NodeNotFoundException {
        Network network = new Network();
        for (LinkElement link : links) {
            link.addTo(network);
        }

        return network;
    }

}
