package network;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Node {

    private Network network;	// network who created the node

    protected int id;   // id must be unique in each network
    protected List<Node> outNeighbours = new ArrayList<>();
    protected List<Link> inLinks = new ArrayList<>();

	/**
     * @param network   network who created the node.
	 * @param id    id to assign to the node.
	 */
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
}