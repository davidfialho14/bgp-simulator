package v2.core;


/**
 * An exporter is responsible for the export operation of the simulation engine. Performs two different operations:
 * the generic exporting of one route through a link and the exporting of the destination.
 */
public interface Exporter {

    /**
     * Exports the given route through the given link.
     *
     * @param link  link to export through.
     * @param route route to export.
     */
    void export(Link link, Route route);

    /**
     * Exports the route to all of the exporting router's in-neighbors.
     *
     * @param exportingRouter router exporting the route
     * @param route route to be exported
     */
    void exportToNeighbors(Router exportingRouter, Route route);

    /**
     * Exports the initial route(s) from the destination router of the simulation. The simulation engine 
     * calls this method to start the simulation process.
     *
     * @param destinationID  ID of the destination router.
     * @param topology       topology bing simulated.
     */
    void exportDestination(int destinationID, Topology topology);

}
