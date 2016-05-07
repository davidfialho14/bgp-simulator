package network;

import network.exceptions.NodeNotFoundException;
import policies.Label;
import policies.Policy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Network {

    private Map<Integer, Node> nodes = new HashMap<>(); // each node must be unique in the network
    private Policy policy;                              // policy of the network

    /**
	 * Creates a new empty network associated with the given policy.
	 */
	public Network(Policy policy) {
        this.policy = policy;
    }

    /**
     * Returns the network policy.
     *
     * @return network policy.
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * Adds a node with the given ID to the network.
     *
     * @param id id of the node to be added to the network.
     * @return true if the node was added or false if there was already a node with the same ID.
     */
	public boolean addNode(int id) {
        Node node = new Node(id);
        if (nodes.putIfAbsent(id, node) != null) {
            return false;   // indicate the node was not added
        }

        // node must store a self link to itself
        node.addOutLink(new SelfLink(node));

        return true; // indicate the node was added
	}

    /**
     * Adds a node to the network.
     *
     * @param node node to be added to the network.
     * @return true if the node was added or false if the node already existed.
     */
    public boolean addNode(Node node) {
        if (nodes.putIfAbsent(node.getId(), node) != null) {
            return false;   // indicate the node was not added
        }

        // node must store a self link to itself
        node.addOutLink(new SelfLink(node));

        return true; // indicate the node was added
    }

    /**
     * Return a collection with the ids of all the nodes in the network.
     *
     * @return collection with the ids of all the nodes in the network.
     */
	public Collection<Integer> getIds() {
		return nodes.keySet();
	}

    /**
     * Returns all the nodes in the network.
     *
     * @return all the nodes in the network.
     */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /**
     * Returns the node with the given id.
     *
     * @param id id of the node to get.
     * @return node with the given id.
     */
    public Node getNode(int id) {
        return nodes.get(id);
    }

    /**
     * Creates a new link between the node with srcId and the node with destId associated with the label and adds
     * the created link to the network.
     * The link is also associated with the given label.
     *
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @param label label to be associated with the link.
     * @throws NodeNotFoundException if one of the ids does not correspond to an existing node.
     */
    public void addLink(int srcId, int destId, Label label) throws NodeNotFoundException {
        Node sourceNode = nodes.get(srcId);
        Node destinationNode = nodes.get(destId);

        if (sourceNode == null || destinationNode == null) {
            int invalidId = sourceNode == null ? srcId : destId;
            throw new NodeNotFoundException(String.format("node with id '%d' does not exist", invalidId));
        }

        Link link = new Link(sourceNode, destinationNode, label);
        sourceNode.addOutLink(link);
        destinationNode.addInLink(link);
    }

    /**
     * Adds a link to the network.
     *
     * @param link link to be added to the network.
     */
    public void addLink(Link link) throws NodeNotFoundException {
        this.addLink(link.getSource().getId(), link.getDestination().getId(), link.getLabel());
    }

    /**
     * Removes the given link from the network. If the link does not exist it returns false.
     *
     * @param link link to be removed
     * @return true if link was removed and false otherwise.
     */
    public boolean removeLink(Link link) {
        Node source = getSource(link);
        Node destination = getDestination(link);

        if (source == null || destination == null ) {
            // nodes do not exist which means the link does not exist!
            return false;
        }

        return source.removeOutLink(link) && destination.removeInLink(link);
    }

    /**
     * Returns a list with all the links in the network.
     *
     * @return list with the links in the network.
     */
    public Collection<Link> getLinks() {
        return nodes.values().stream()
                .flatMap(node -> node.getInLinks().stream())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        String networkStr = "Network{";

        for (Node node : nodes.values()) {
            for (Link link : node.getInLinks()) {
                networkStr += "\n\t" + link;
            }
        }

        return networkStr + "\n}";
    }

    // ----- PRIVATE -----

    /**
     * Returns the node in the network corresponding to the source node of the given link.
     *
     * @param link link to get source node from.
     * @return node in the network corresponding to the source node of the given link.
     */
    private Node getSource(Link link) {
        return nodes.get(link.getSource().getId());
    }

    /**
     * Returns the node in the network corresponding to the destination node of the given link.
     *
     * @param link link to get destination node from.
     * @return node in the network corresponding to the destination node of the given link.
     */
    private Node getDestination(Link link) {
        return nodes.get(link.getDestination().getId());
    }
}