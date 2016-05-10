package networks.factories;

import network.Network;
import policies.shortestpath.ShortestPathPolicy;

import static wrappers.ShortestPathWrapper.label;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;

public class ShortestPathNetworkFactory implements NetworkFactory {

    private final static ShortestPathPolicy SHORTEST_PATH_POLICY = new ShortestPathPolicy();

    private final static Network[] networks = {
            network(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(1))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(0), to(1), label(1)),
                    link(from(1), to(2), label(1)),
                    link(from(0), to(2), label(0))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(0), to(1), label(1)),
                    link(from(1), to(2), label(1)),
                    link(from(2), to(0), label(1))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(0)),
                    link(from(2), to(0), label(0)),
                    link(from(3), to(0), label(0)),
                    link(from(1), to(2), label(-1)),
                    link(from(2), to(3), label(1)),
                    link(from(3), to(1), label(-2))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(0)),
                    link(from(1), to(2), label(1)),
                    link(from(2), to(3), label(1)),
                    link(from(3), to(1), label(1))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(2), to(1), label(1)),
                    link(from(1), to(0), label(1))
            ),
            network(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(0)),
                    link(from(2), to(0), label(5)),
                    link(from(3), to(1), label(1)),
                    link(from(3), to(2), label(5)),
                    link(from(3), to(4), label(1)),
                    link(from(4), to(5), label(1)),
                    link(from(5), to(3), label(1))
            ),
    };

    /**
     * Creates a network instance initialized according to the network ID given.
     *
     * @param networkId id of the network to create
     * @return network created.
     */
    @Override
    public Network create(int networkId) {
        return new Network(networks[networkId]);
    }

}
