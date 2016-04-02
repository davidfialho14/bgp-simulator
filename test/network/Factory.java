package network;

import policies.DummyAttribute;
import policies.DummyLabel;
import simulation.PathAttribute;
import simulation.Route;

/**
 * Factory class to create instances necessary for abstract and interface classes necessary to build a network.
 * The created elements are guaranteed to be able to work together.
 */
public class Factory {

    /**
     * Creates a node with a fixed Id. All nodes created with this method will have the same id. All the other node
     * attributes will be initialized to null.
     * @return new node instance.
     */
    public static Node createNode() {
        return new Node(null, 0);
    }

    /**
     * Creates a node with the specified id. All the other node attributes will be initialized to null.
     * @return new node instance.
     */
    public static Node createNode(int id) {
        return new Node(null, id);
    }

    /**
     * Creates n node instances with different ids. The returned nodes are ordered by id from the smaller id to the
     * higher. All the other node attributes will be initialized to null.
     * @return array with n node instances.
     */
    public static Node[] createNodes(int n) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(null, i);
        }

        return nodes;
    }

    // used to create random nodes with different ids
    private static int nodeId = 0;

    /**
     * Creates a node with random id. The ids will never repeat between calls. All the other node attributes will be
     * initialized to null.
     * @return new node instance.
     */
    public static Node createRandomNode() {
        return new Node(null, nodeId++);
    }

    /**
     * Creates n nodes with random ids. The ids will never repeat between calls. All the other node attributes will be
     * initialized to null.
     * @return array with the created nodes.
     */
    public static Node[] createRandomNodes(int n) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(null, nodeId++);
        }

        return nodes;
    }

    /**
     * Creates a link between two nodes of a network with dummy label. It creates the nodes from the ids.
     * To be used when a link is needed but it label its not used.
     * @param network network where node are to be linked.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @return link instance.
     */
    public static Link createLink(Network network, int srcId, int destId) {
        // do not care about the link length
        return new Link(new Node(network, srcId),
                new Node(network, destId), null);
    }

    /**
     * The same as the previous method but with the network being initialized to null.
     */
    public static Link createLink(int srcId, int destId) {
        // do not care about the link length
        return new Link(new Node(null, srcId), new Node(null, destId), null);
    }

    /**
     * Creates a link between two random nodes and associated with a dummy label. The nodes are initialized with
     * a null network and protocol. The node's ids will never repeat between calls, which mean it will always
     * create different links.
     * @return link instance.
     */
    public static Link createRandomLink() {
        return new Link(createRandomNode(), createRandomNode(), new DummyLabel());
    }

    /**
     * Creates a route with random destination. Initialized with a dummy attribute and empty path. The destination
     * nodes created between calls never repeat, this means there will always be created different routes.
     * @return new route instance.
     */
    public static Route createRandomRoute() {
        return new Route(createRandomNode(), new DummyAttribute(), new PathAttribute());
    }

    /**
     * Creates n routes instances with different random destination nodes.
     * @return array with n node instances.
     */
    public static Route[] createRandomRoutes(int n) {
        Route[] routes = new Route[n];
        for (int i = 0; i < n; i++) {
            routes[i] = new Route(createRandomNode(), new DummyAttribute(), new PathAttribute());
        }

        return routes;
    }

}
