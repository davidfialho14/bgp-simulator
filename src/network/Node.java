package network;


import java.util.*;

public class Node {

    private int id;   // id must be unique in each network

    // can only have one for link for each neighbour
    private Map<Node, Link> outLinks = new HashMap<>();
    private Map<Node, Link> inLinks = new HashMap<>();

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
        this.outLinks = new HashMap<>(node.outLinks);
        this.inLinks = new HashMap<>(node.inLinks);
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
        return outLinks.put(link.getDestination(), link) != null;
    }

    /**
     * Adds a new in-link to the node.
     * @param link link to be added as in-link.
     * @return true if the link was added and false otherwise.
     */
    public boolean addInLink(Link link) {
        return inLinks.put(link.getSource(), link) != null;
    }

    /**
     * Removes the given link from the set of out-links of the node.
     *
     * @param link link to be removed.
     * @return true if the link existed and was removed and false otherwise.
     */
    public boolean removeOutLink(Link link) {
        return outLinks.remove(link.getDestination()) != null;
    }

    /**
     * Removes the given link from the set of in-links of the node.
     *
     * @param link link to be removed.
     * @return true if the link existed and was removed and false otherwise.
     */
    public boolean removeInLink(Link link) {
        return inLinks.remove(link.getSource()) != null;
    }

    /**
     * Returns a collection with all the in-links of the node.
     * @return collection with all the in-links of the node.
     */
    public Collection<Link> getInLinks() {
        return inLinks.values();
    }

    /**
     * Returns a collection with all the out-links of the node.
     * @return collection with all the out-links of the node.
     */
    public Collection<Link> getOutLinks() {
        return outLinks.values();
    }

    /**
     * Returns the out-link to a neighbour.
     *
     * @param neighbour neighbour to get out-link for.
     * @return out-link to the neighbour or null if there is not out-link for that neighbour.
     */
    public Link getOutLink(Node neighbour) {
        return outLinks.get(neighbour);
    }

    /**
     * Returns the in-link to a neighbour.
     *
     * @param neighbour neighbour to get in-link for.
     * @return in-link to the neighbour or null if there is not in-link for that neighbour.
     */
    public Link getInLink(Node neighbour) {
        return inLinks.get(neighbour);
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