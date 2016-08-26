package core.topology;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * A connected node is a Node implementation that stores the links between neighbours of the node. Connected nodes
 * are used by the network. A connected node stores both in and out-link with other nodes. When node U is the source
 * of link L, L is an out-link of U. If node U is the destination of L, then L is an in-link of U.
 * <p>
 * To copy a node its copy constructor must be used.
 */
public class ConnectedNode extends BaseNode{

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // can only have one for link for each neighbour
    private Map<ConnectedNode, Link> outLinks = new HashMap<>();
    private Map<ConnectedNode, Link> inLinks = new HashMap<>();

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
    public ConnectedNode(int id) {
        super(id);
    }

    /**
     * Copy constructor. Nodes can only be copied safely using this constructor, otherwise it might result in undefined
     * behaviour.
     *
     * @param node node to copy.
     */
    public ConnectedNode(ConnectedNode node) {
        super(node.getId());
        this.outLinks = new HashMap<>(node.outLinks);
        this.inLinks = new HashMap<>(node.inLinks);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Getters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
    public Link getOutLink(ConnectedNode neighbour) {
        return outLinks.get(neighbour);
    }

    /**
     * Returns the in-link to a neighbour.
     *
     * @param neighbour neighbour to get in-link for.
     * @return in-link to the neighbour or null if there is not in-link for that neighbour.
     */
    public Link getInLink(ConnectedNode neighbour) {
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

}