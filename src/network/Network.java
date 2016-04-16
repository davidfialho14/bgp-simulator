package network;

import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import policies.Label;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Network {

    private Map<Integer, Node> nodes = new HashMap<>(); // each node must be unique in the network

    /**
	 * Creates a new empty network.
	 */
	public Network() {
	}

	/**
     * Adds a new node to the network with the given id.
     * @param id id of the node to be added to the network.
     * @throws NodeExistsException if a node with the given id already exists in the network.
     */
	public void addNode(int id) throws NodeExistsException {
        Node node = new Node(id);
		if (nodes.putIfAbsent(id, node) != null) {
            throw new NodeExistsException(String.format("node with id '%d' already exists", id));
        }
	}

    /**
     * Return a collection with the ids of all the nodes in the network.
     * @return collection with the ids of all the nodes in the network.
     */
	public Set<Integer> getIds() {
		return nodes.keySet();
	}

    /**
     * Returns all the nodes in the network.
     * @return all the nodes in the network.
     */
    public Collection<Node> getNodes() {
        return nodes.values();
    }

    /**
     * Returns the node associated with the given id.
     * @param id id of the node to get.
     * @return node associated with the given id.
     */
    public Node getNode(int id) {
        return nodes.get(id);
    }

    /**
     * Creates a link between the node with id srcId and the node with id destId.
     * The link is also associated with the given label.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @param label label to be associated with the link.
     * @throws NodeNotFoundException if one of the ids does not correspond to an existing node.
     */
    public void link(int srcId, int destId, Label label) throws NodeNotFoundException {
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
     * Returns a list with all the links in the network.
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
}