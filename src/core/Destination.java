package core;

import java.util.Collection;

import static core.protocols.DummyDetection.dummyDetection;

/**
 * A destination is a tag interface to indicate that a node is the destination of the simulation. A router
 * must implement the destination interface. This interface only includes methods export the initial routes
 * of the simulation from the destination router to all of its in-neighbors.
 */
public interface Destination {

    /**
     * Creates a destination object based on a router. The destination is created without any neighbors.
     *
     * @param id ID to assign the destination.
     * @return destination object initialized with the given ID.
     */
    static Destination newDestination(int id) {
        // the MRAI value and the detection method of a destination does not matter
        // neither of them are used
        return new Router(id, 0, dummyDetection());
    }

    /**
     * Returns the destination's ID.
     *
     * @return the destination's ID.
     */
    int getId();

    /**
     * Returns a collection with all the in-links of the destination router.
     *
     * @return collection with all the in-links of the destination router.
     */
    Collection<Link> getInLinks();

    /**
     * Returns the number of in-neighbors the router has.
     *
     * @return the number of in-neighbors the router has.
     */
    int getInNeighborCount();

    /**
     * Adds a new in-neighbor to the destination router. It associates the neighbor with the given label. If
     * this destination router already has the given destination router as a neighbor then the given label
     * replaces the previous one. It creates a new link with the neighbor.
     *
     * @param neighbor  router to add as neighbor.
     * @param label     label to model the relationship with the neighbor or the cost of the link to it.
     */
    void addInNeighbor(Router neighbor, Label label);

    /**
     * Sets the self route for the destination router.
     *
     * @param selfRoute  self route to set.
     */
    void setSelfRoute(Route selfRoute);

    /**
     * Returns the MRAI timer associated with the destination.
     *
     * @return the MRAI timer associated with the destination.
     */
    MRAITimer getMRAITimer();

}
