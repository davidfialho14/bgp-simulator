package core;


import core.exceptions.RouterNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Data structure to represent the network topology. It is an high level abstraction of a network graph
 * modelling the routers and the connections between themselves. It also associate with this the routing
 * policy model applied by all routers. This does not mean that all routers apply exactly the same routing
 * policies (refer to the Algebraic Model to better understand this concept).
 */
public final class Topology {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Policy policy;
    private Protocol protocol;
    private final Map<Integer, Router> routers = new HashMap<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a new topology associating with the policy.
     *
     * @param policy  topology policy model.
     */
    public Topology(Policy policy, Protocol protocol) {
        this.policy = policy;
        this.protocol = protocol;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the topology's routing policy.
     *
     * @return the topology's routing policy.
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * Returns the protocol deployed by the topology's routers.
     *
     * @return the protocol deployed by the topology's routers.
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * Return a collection with the IDs of all routers in the topology.
     *
     * @return collection with the IDs of all routers in the topology.
     */
    public Collection<Integer> getIds() {
        return routers.keySet();
    }

    /**
     * Returns all the routers in the topology.
     *
     * @return all the routers in the topology.
     */
    public Collection<Router> getRouters() {
        return routers.values();
    }

    /**
     * Looks up the router with the given ID and returns it.
     *
     * @param id ID of the router to look up for.
     * @return router with the given ID.
     */
    public Router getRouter(int id) {
        return routers.get(id);
    }

    /**
     * Returns the number of routers in the topology.
     *
     * @return number of routers in the topology.
     */
    public int getRouterCount() {
        return routers.size();
    }

    /**
     * Returns the number of links in the topology.
     *
     * @return number of links in the topology.
     */
    public int getLinkCount() {

        int linkCount = 0;
        for (Router router : routers.values()) {
            linkCount += router.getInNeighborCount();
        }

        return linkCount;
    }

    /**
     * Returns a collection containing all the links in the topology.
     *
     * @return collection containing all the links in the topology.
     */
    public Collection<Link> getLinks() {
        Collection<Link> links = new ArrayList<>();

        for (Router router : routers.values()) {
            links.addAll(router.getInLinks());
        }

        return links;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Modifiers
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets the protocol to be deployed by the routers of the topology.
     *
     * @param protocol protocol to be deployed.
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    /**
     * Adds a router to the topology. If the router already exists in the topology it returns false,
     * otherwise, it returns true.
     *
     * @param router router to be added to the topology.
     * @return true if the router was added or false if the router already existed.
     */
    public boolean addRouter(Router router) {
        boolean added = routers.putIfAbsent(router.getId(), router) == null;
        if (added) router.setTopology(this);

        return added;
    }

    /**
     * Adds a new link to the topology linking two routers and assigning them a label to model their
     * relationship or the cost of the link between them. It looks up the routers to connect from the
     * routers already belonging to the topology. If any of the IDs does not match any of the routers in
     * the topology then a RouterNotFoundException is thrown. If this two routers are already linked, the
     * previous link is replaced with the new one.
     *
     * @param srcRouterId  source router of the link.
     * @param targetRouterId  target router of the link.
     * @param label label modelling the relationship between the routers  or the cost of the link.
     * @throws RouterNotFoundException if one of the ids does not correspond to an existing router.
     */
    public void link(int srcRouterId, int targetRouterId, Label label) throws RouterNotFoundException {
        Router sourceRouter = routers.get(srcRouterId);
        Router targetRouter = routers.get(targetRouterId);

        // check if both routers exist in the topology
        if (sourceRouter == null || targetRouter == null) {
            // get the ID of the first router that was not found
            int invalidId = sourceRouter == null ? srcRouterId : targetRouterId;

            throw new RouterNotFoundException(String.format("router with ID '%d' does not exist", invalidId));
        }

        // link routers
        targetRouter.addInNeighbor(sourceRouter, label);
    }

    /**
     * Adds a new link to the topology linking the source and target routers. Associates the link with a label
     * modelling the relationship between the two routers or the cost of the link. If this two routers are
     * already linked, then the previous link is replaced with the new one. If one the given routers does
     * not exist in the topology then it is added to the topology and the link is created.
     *
     * @param sourceRouter  source router of the link.
     * @param targetRouter  target router of the link.
     * @param label label modelling the relationship between the routers  or the cost of the link.
     */
    public void link(Router sourceRouter, Router targetRouter, Label label) {
        addRouter(sourceRouter);    // ensure both routers belong to the topology
        addRouter(targetRouter);    // ensure both routers belong to the topology

        // link routers
        targetRouter.addInNeighbor(sourceRouter, label);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Operator Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Maps the topology into a string in Graphviz format:
     *
     * Topology {
     *     policy: shortest path
     *
     *     1 -> 2
     *     3 -> 1
     * }
     *
     * @return string in Graphviz format to represent the topology.
     */
    @Override
    public String toString() {
        String networkStr = "Topology {\n" +
                "\tpolicy: " + policy + "\n\n";

        for (Router router: routers.values()) {
            for (Link link : router.getInLinks()) {
                networkStr += "\n\t" + link;
            }
        }

        return networkStr + "\n}";
    }

}
