package v2.core;

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
public class Router extends Node {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    
    private Map<Router, Link> inLinks = new HashMap<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new router with the given ID.
     *
     * @param id id to assign to the router.
     */
    public Router(int id) {
        super(id);
    }

    // TODO create constructors to configure the MRAI timer and the protocol

    /**
     * Copy constructor. Nodes can only be copied safely using this constructor, otherwise it might result in undefined
     * behaviour.
     *
     * @param router router to copy.
     */
    public Router(Router router) {
        super(router.getId());
        this.inLinks = new HashMap<>(router.inLinks);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Getters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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

}