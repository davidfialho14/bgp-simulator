package factories;

import core.topology.Topology;
import policies.gaorexford.GaoRexfordPolicy;
import wrappers.topology.TopologyWrapper;

import static wrappers.GaoRexfordWrapper.customerLabel;
import static wrappers.GaoRexfordWrapper.providerLabel;
import static wrappers.topology.FromNodeElement.from;
import static wrappers.topology.LinkElement.link;
import static wrappers.topology.ToNodeElement.to;

public class GaoRexfordTopologyFactory implements TopologyFactory {

    private final static GaoRexfordPolicy GAO_REXFORD_POLICY = new GaoRexfordPolicy();

    private final static Topology[] topologies = {
            TopologyWrapper.topology(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(0), providerLabel())
            ),
            TopologyWrapper.topology(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(0), providerLabel()),
                    link(from(2), to(1), customerLabel()),
                    link(from(1), to(2), providerLabel())
            ),
            TopologyWrapper.topology(GAO_REXFORD_POLICY,
                    link(from(0), to(1), customerLabel()),
                    link(from(1), to(2), customerLabel()),
                    link(from(2), to(0), customerLabel())
            ),
    };

    /**
     * Creates a topology instance initialized according to the topology ID given.
     *
     * @param id id of the topology to create
     * @return topology created.
     */
    @Override
    public Topology topology(int id) {
        return new Topology(topologies[id]);
    }

}
