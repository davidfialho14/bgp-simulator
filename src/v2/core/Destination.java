package v2.core;

import java.util.Collection;

/**
 * A destination is a tag interface to indicate that a node is the destination of the simulation. A router
 * must implement the destination interface. This interface only includes methods export the initial routes
 * of the simulation from the destination router to all of its in-neighbors.
 */
public interface Destination {

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

}
