package v2.core;

import v2.core.exporters.Exporter;
import v2.core.protocols.Detection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * A router is an high-level abstraction of a BGP speaker. along with links, the router is one 
 * of the most basic components of a topology. A router contains information about its current state, such 
 * as the current routes available to the destination and the its current selected route. It is based on a 
 * node and uses its ID as the AS number. Besides state, a router keeps track of its current available
 * connections, storing the links with all of its neighbors. Each router is associated with a protocol that
 * is used to process incoming messages. 
 */
public class Router extends Node implements Destination {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Topology topology = null;    // topology to which the router belongs to
    private final Map<Router, Link> inLinks;
    private RouteTable table = new RouteTable();

    private final MRAITimer mraiTimer;
    private Detection detection;    // detection method deployed by the router

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new router and sets the MRAI value for the timer.
     *
     * @param id        ID to assign to the router.
     * @param MRAI      value for the MRAI.
     * @param detection detection method deployed by the router.
     */
    public Router(int id, int MRAI, Detection detection) {
        super(id);
        inLinks = new HashMap<>();
        this.mraiTimer = new MRAITimer(MRAI);
        this.detection = detection;
    }

    /**
     * Copy constructor. Nodes can only be copied safely using this constructor, otherwise it might result
     * in undefined behaviour.
     *
     * @param router router to copy.
     */
    public Router(Router router) {
        super(router.getId());
        this.inLinks = new HashMap<>(router.inLinks);
        this.mraiTimer = new MRAITimer(router.getMRAITimer().getMRAI());
        this.detection = router.getDetection();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets the topology of the router. The router will use the topology to get configurations of it.
     *
     * @param topology  topology to set.
     */
    public void setTopology(Topology topology) {
        this.topology = topology;
    }

    /**
     * Returns the detection method deployed by the router.
     *
     * @return the detection method deployed by the router.
     */
    public Detection getDetection() {
        return detection;
    }

    /**
     * Sets the detection method to be deployed by the router.
     *
     * @param detection detection method to be deployed.
     */
    public void setDetection(Detection detection) {
        this.detection = detection;
    }

    /**
     * Returns a collection with all the in-links of the router.
     *
     * @return collection with all the in-links of the router.
     */
    public Collection<Link> getInLinks() {
        return inLinks.values();
    }
    
    /**
     * Returns the in-link to a neighbour.
     *
     * @param neighbour neighbour to get in-link for.
     * @return in-link to the neighbour or null if there is not in-link for that neighbour.
     */
    public Link getInLink(Router neighbour) {
        return inLinks.get(neighbour);
    }

    /**
     * Returns the number of in-neighbors the router has.
     *
     * @return the number of in-neighbors the router has.
     */
    public int getInNeighborCount() {
        return inLinks.size();
    }

    /**
     * Returns the MRAI timer associated with the router.
     *
     * @return the MRAI timer associated with the router.
     */
    public MRAITimer getMRAITimer() {
        return mraiTimer;
    }

    /**
     * Adds a new in-neighbor to the router. It associates the neighbor with the given label. If this
     * router already has the given router as a neighbor then the given label replaces the previous one. It
     * creates a new link with the neighbor.
     *
     * @param neighbor  router to add as neighbor.
     * @param label     label to model the relationship with the neighbor or the cost of the link to it.
     */
    public void addInNeighbor(Router neighbor, Label label) {
        // adds a new in-link to the router
        inLinks.put(neighbor, new Link(neighbor, this, label));
    }

    /**
     * Returns the route table of the router in its current state.
     *
     * @return the route table of the router in its current state.
     */
    public RouteTable getTable() {
        return table;
    }

    /**
     * Sets the self route for the destination router.
     *
     * @param selfRoute  self route to set.
     */
    @Override
    public void setSelfRoute(Route selfRoute) {
        table.setRoute(this, selfRoute);
    }

    /**
     * Calls the routers protocol process method to process the incoming message.
     *
     * @param message   message to be processed.
     * @param exporter  exporter used to export a message if necessary.
     */
    public void process(Message message, Exporter exporter) {
        topology.getProtocol().process(message, exporter);
    }

    @Override
    public String toString() {
        return "Router(" + id + ")";
    }

}