package network;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Node {

    private int id;   // id must be unique in each network
    private Set<Link> outLinks = new HashSet<>();
    private Set<Link> inLinks = new HashSet<>();

    /**
	 * @param id    id to assign to the node.
	 */
    public Node(int id) {
		this.id = id;
	}

    /**
     * Copy constructor.
     *
     * @param node node to copy.
     */
    public Node(Node node) {
        this.id = node.id;
        this.outLinks = new HashSet<>(node.outLinks);
        this.inLinks = new HashSet<>(node.inLinks);
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
     * @return true if the link was added and false otherwise.
     */
    public boolean addOutLink(Link link) {
        return outLinks.add(link);
    }

    /**
     * Adds a new in-link to the node.
     * @param link link to be added as in-link.
     * @return true if the link was added and false otherwise.
     */
    public boolean addInLink(Link link) {
        return inLinks.add(link);
    }

    /**
     * Removes the given link from the set of out-links of the node.
     *
     * @param link link to be removed.
     * @return true if the link existed and was removed and false otherwise.
     */
    public boolean removeOutLink(Link link) {
        return outLinks.remove(link);
    }

    /**
     * Removes the given link from the set of in-links of the node.
     *
     * @param link link to be removed.
     * @return true if the link existed and was removed and false otherwise.
     */
    public boolean removeInLink(Link link) {
        return inLinks.remove(link);
    }

    /**
     * Returns a collection with all the in-links of the node.
     * @return collection with all the in-links of the node.
     */
    public Collection<Link> getInLinks() {
        return inLinks;
    }

    /**
     * Returns a collection with all the out-links of the node.
     * @return collection with all the out-links of the node.
     */
    public Collection<Link> getOutLinks() {
        return outLinks;
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