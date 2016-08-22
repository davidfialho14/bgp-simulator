package core.topology;

/**
 * A topology is a read only class that associates a topology representation with a routing policy.
 */
public final class Topology {

    private final Network network;
    private final Policy policy;

    /**
     * Constructs a new topology associating the two given topology and policy.
     *
     * @param network topology topology
     * @param policy  topology routing policy
     */
    public Topology(Network network, Policy policy) {
        this.network = network;
        this.policy = policy;
    }

    /**
     * Copy constructor. Creates a new copy of the given topology.
     *
     * @param topology topology to copy.
     */
    public Topology(Topology topology) {
        this.network = new Network(topology.getNetwork());
        this.policy = topology.getPolicy(); // policy classes are immutable
    }

    /**
     * Returns the topology's topology.
     *
     * @return the topology's topology
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
