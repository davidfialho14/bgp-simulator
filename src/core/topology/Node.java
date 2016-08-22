package core.topology;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * A node is one of the two fundamental elements of a topology. Nodes are identified by an ID which must be unique for
 * each node. Nodes with the same ID represent the same node (are equal). Nodes may be connected with other nodes
 * through links. A node stores both in and out-link with other nodes. When node U is the source of link L, L is an
 * out-link of U. If node U is the destination of L, then L is an in-link of U.
 * <p>
 * Nodes can not be directly copied. To copy a node its copy constructor must be used.
 */
public class Node {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final int id;   // unique identifier for the node

    // can only have one for link for each neighbour
    private Map<Node, Link> outLinks = new HashMap<>();
    private Map<Node, Link> inLinks = new HashMap<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new node with the given ID.
     *
     * @param id id to assign to the node.
     */
    public Node(int id) {
        this.id = id;
    }

    /**
     * Copy constructor. Nodes can only be copied safely using this constructor, otherwise it might result in undefined
     * behaviour.
     *
     * @param node node to copy.
     */
    public Node(Node node) {
        this.id = node.id;
        this.outLinks = new HashMap<>(node.outLinks);
        this.inLinks = new HashMap<>(node.inLinks);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Getters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the id of the node.
     *
     * @return id of the node.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns a collection with all the in-links of the node.
     *
     * @return collection with all the in-links of the node.
     */
    public Collection<Link> getInLinks() {
        return inLinks.values();
    }

    /**
     * Returns a collection with all the out-links of the node.
     *
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Modifier Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Adds a new out-link to the node.
     *
     * @param link link to be added as out-link.
     * @return true if the link was added and false otherwise.
     */
    public boolean addOutLink(Link link) {
        return outLinks.put(link.getDestination(), link) != null;
    }

    /**
     * Adds a new in-link to the node.
     *
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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Operator Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Two nodes are equal if and only if they have the same id.
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