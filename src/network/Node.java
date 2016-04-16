package network;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Node {

    private int id;   // id must be unique in each network
    private List<Node> outNeighbours = new ArrayList<>();
    private List<Link> inLinks = new ArrayList<>();

    /**
	 * @param id    id to assign to the node.
	 */
    public Node(int id) {
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

    /**
     * Returns a collection with all the out-neighbours of the node.
     * @return collection with all the out-neighbours of the node.
     */
    public Collection<Node> getOutNeighbours() {
        return outNeighbours;
    }

    /**
     * Two nodes are equal if they have the same id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return id == node.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node(" + id + ')';
    }

}