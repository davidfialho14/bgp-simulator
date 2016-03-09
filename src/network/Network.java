package network;

import network.exceptions.NodeExistsException;

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
     * @throws NodeExistsException if a node with the given id already exists in the network.
     */
	public void addNode(int id) throws NodeExistsException {
		if (!nodes.add(nodeFactory.createNode(this, id))) {
            throw new NodeExistsException(String.format("node with id '%d' already exists", id));
        }
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