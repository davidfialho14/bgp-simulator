package factories;

import core.topology.Topology;
import policies.shortestpath.ShortestPathPolicy;
import wrappers.topology.TopologyWrapper;

import static wrappers.ShortestPathWrapper.label;
import static wrappers.topology.FromNodeElement.from;
import static wrappers.topology.LinkElement.link;
import static wrappers.topology.ToNodeElement.to;

public class ShortestPathTopologyFactory implements TopologyFactory {

    private final static ShortestPathPolicy SHORTEST_PATH_POLICY = new ShortestPathPolicy();

    private final static Topology[] topologies = {
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(1))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(0), to(1), label(1)),
                    link(from(1), to(2), label(1)),
                    link(from(0), to(2), label(0))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(0), to(1), label(1)),
                    link(from(1), to(2), label(1)),
                    link(from(2), to(0), label(1))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(0)),
                    link(from(2), to(0), label(0)),
                    link(from(3), to(0), label(0)),
                    link(from(1), to(2), label(-1)),
                    link(from(2), to(3), label(1)),
                    link(from(3), to(1), label(-2))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(1), to(0), label(0)),
                    link(from(1), to(2), label(1)),
                    link(from(2), to(3), label(1)),
                    link(from(3), to(1), label(1))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
                    link(from(2), to(1), label(1)),
                    link(from(1), to(0), label(1))
            ),
            TopologyWrapper.topology(SHORTEST_PATH_POLICY,
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
