package network;

import java.util.*;
import java.util.stream.Collectors;

public class Network {

	private Set<Node> nodes = new HashSet<>();  // each node must be unique in the network
	private NodeFactory nodeFactory;

	/**
	 * Creates a new empty network.
	 * @param nodeFactory factory used to create nodes for the network.
	 */
	public Network(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}

	/**
	 * Adds a new node to the network with the given id.
	 * @param id id of the node to be added to the network.
	 */
	public void addNode(int id) {
		nodes.add(nodeFactory.createNode(this, id));
	}

    /**
     * Return a collection with the ids of all the nodes in the network.
     * @return collection with the ids of all the nodes in the network.
     */
	public Collection<Integer> getIds() {
		return nodes.stream()
                .map(Node::getId)
                .collect(Collectors.toList());
	}

}