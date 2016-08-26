package core.topology;

import core.Path;
import core.Route;
import stubs.StubAttribute;
import stubs.StubLabel;

import static wrappers.RouteWrapper.route;

/**
 * Factory class to create instances necessary for abstract and interface classes necessary to build a topology.
 * The created elements are guaranteed to be able to work together.
 */
public class Factory {

    // used to create random nodes with different ids
    private static int nodeId = 0;

    /**
     * Creates n node instances with different ids. The returned nodes are ordered by id from the smaller id to the
     * higher. All the other node attributes will be initialized to null.
     * @return array with n node instances.
     */
    public static ConnectedNode[] createNodes(int n) {
        ConnectedNode[] nodes = new ConnectedNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new ConnectedNode(i);
        }

        return nodes;
    }

    /**
     * Creates a node with random id. The ids will never repeat between calls. All the other node attributes will be
     * initialized to null.
     * @return new node instance.
     */
    public static ConnectedNode createRandomNode() {
        return new ConnectedNode(nodeId++);
    }

    /**
     * Creates n nodes with random ids. The ids will never repeat between calls. All the other node attributes will be
     * initialized to null.
     * @return array with the created nodes.
     */
    public static ConnectedNode[] createRandomNodes(int n) {
        ConnectedNode[] nodes = new ConnectedNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new ConnectedNode(nodeId++);
        }

        return nodes;
    }

    /**
     * Creates a link between two nodes with dummy label. It creates the nodes from the ids.
     * To be used when a link is needed but it label its not used.
     * @param srcId id of the source node.
     * @param destId id of the destination node.
     * @return link instance.
     */
    public static Link createLink(int srcId, int destId) {
        // do not care about the link length
        return new Link(new ConnectedNode(srcId),
                new ConnectedNode(destId), null);
    }

    /**
     * Creates a link between two random nodes and associated with a dummy label. The node's ids will never repeat
     * between calls, which mean it will always create different links.
     * @return link instance.
     */
    public static Link createRandomLink() {
        return new Link(createRandomNode(), createRandomNode(), new StubLabel());
    }

    /**
     * Creates n links instances with different source and destination nodes.
     * @return array with n link instances.
     */
    public static Link[] createRandomLinks(int n) {
        Link[] links = new Link[n];
        for (int i = 0; i < n; i++) {
            links[i] = createRandomLink();
        }

        return links;
    }

    /**
     * Creates a route with random destination. Initialized with a dummy attribute and empty path. The destination
     * nodes created between calls never repeat, this means there will always be created different routes.
     * @return new route instance.
     */
    public static Route createRandomRoute() {
        return route(createRandomNode(), new StubAttribute(), new Path());
    }

    /**
     * Creates n routes instances with different random destination nodes.
     * @return array with n route instances.
     */
    public static Route[] createRandomRoutes(int n) {
        Route[] routes = new Route[n];
        for (int i = 0; i < n; i++) {
            routes[i] = route(createRandomNode(), new StubAttribute(), new Path());
        }

        return routes;
    }

}