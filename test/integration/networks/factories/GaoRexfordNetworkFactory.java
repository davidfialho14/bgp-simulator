package networks.factories;

import network.Network;
import policies.gaorexford.GaoRexfordPolicy;

import static wrappers.GaoRexfordWrapper.customerLabel;
import static wrappers.GaoRexfordWrapper.providerLabel;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;

public class GaoRexfordNetworkFactory implements NetworkFactory {

    private final static GaoRexfordPolicy GAO_REXFORD_POLICY = new GaoRexfordPolicy();

    private final static Network[] networks = {
            network(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(0), providerLabel())
            ),
            network(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(0), providerLabel()),
                    link(from(2), to(1), customerLabel()),
                    link(from(1), to(2), providerLabel())
            ),
            network(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(2), customerLabel()),
                    link(from(2), to(0), customerLabel())
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
