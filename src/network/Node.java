package network;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Node {

    private Network network;    // network who created the node

    private int id;   // id must be unique in each network
    private List<Node> outNeighbours = new ArrayList<>();
    private List<Link> inLinks = new ArrayList<>();

    // fields used during simulation
    private RouteTable routeTable;

	/**
     * @param network   network who created the node.
	 * @param id    id to assign to the node.
	 */
    // TODO add a protocol to the constructor
    // TODO make the constructor public and remove the classes NodeFactory, BGPNode, BGPNodeFactory
    protected Node(Network network, int id) {
		this.network = network;
		this.id = id;
	}

    /**
     * Returns the id of the node.
     * @return id of the node.
     */
	public int getId() {
		return this.id;
	}

    /**
     * Adds a new out-link to the node.
     * @param link link to be added as out-link.
     */
    public void addOutLink(Link link) {
        outNeighbours.add(link.getDestination());
    }

    /**
     * Adds a new in-link to the node.
     * @param link link to be added as in-link.
     */
    public void addInLink(Link link) {
        inLinks.add(link);
    }

    /**
     * Returns a collection with all the in-links of the node.
     * @return collection with all the in-links of the node.
     */
    public Collection<Link> getInLinks() {
        return inLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        return network != null ? network.equals(node.network) : node.network == null;

    }

    @Override
    public int hashCode() {
        int result = network != null ? network.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    /**
     * Resets the route table and exports the node's self route to all of its in-neighbours.
     * This method must be called before simulating the network and should only be called by the network class.
     */
    void start() {
        try {
            routeTable.clear();
        } catch (NullPointerException e) {
            routeTable = new RouteTable(outNeighbours);
        }

        for (Link inLink : inLinks) {
            export(inLink, new Route(this, network.attrFactory.createSelf(this), new PathAttribute(this)));
        }
    }

    /**
     * Exports a route through the given link. The route is put in the network's scheduler.
     * @param link link to export the route to.
     * @param route route to be exported.
     */
    private void export(Link link, Route route) {
        // TODO - implement Node.export
        throw new UnsupportedOperationException();
    }
}