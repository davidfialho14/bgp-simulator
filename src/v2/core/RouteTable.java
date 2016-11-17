package v2.core;


import dnl.utils.text.table.TextTable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;

import static v2.core.InvalidRoute.invalidRoute;


/**
 * Stores the routes learned from each out-neighbor to reach the destination router.
 */
public class RouteTable {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Map<Router, Route> routes;

    private Route selectedRoute;
    private Router selectedNeighbour;

    // flag to indicate if a new route was selected after the last time it is checked
    private boolean selectedNewRoute = false;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Constructs a new empty route table with no out-neighbors.
     */
    public RouteTable() {
        this.routes = new HashMap<>();
        this.selectedRoute = invalidRoute();
        this.selectedNeighbour = null;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the currently selected route.
     *
     * @return the currently selected route.
     */
    public Route getSelectedRoute() {
        return selectedRoute;
    }

    /**
     * Returns the currently selected neighbour.
     *
     * @return the currently selected neighbour.
     */
    public Router getSelectedNeighbour() {
        return selectedNeighbour;
    }

    /**
     * Sets the route for an out-neighbor. If the out-neighbor does not exist in the table, it will be
     * added and assigned to the new route. Calling this method may update the selected route.
     *
     * @param neighbor out-neighbor to update route for.
     * @param route route to be set.
     */
    public void setRoute(Router neighbor, Route route) {
        routes.put(neighbor, route);

        if (neighbor.equals(selectedNeighbour)) {
            // the selected route is no longer valid
            // re-select the best route
            reselect();
            selectedNewRoute = true;

        } else if (route.compareTo(selectedRoute) < 0) {
            selectedRoute = route;
            selectedNeighbour = neighbor;
            selectedNewRoute = true;
        }

    }

    /**
     * Checks the the selected route was changed after the last time this method was called.
     *
     * @return true if the route was selected after the last time this method was called and false if
     *         otherwise.
     */
    public boolean selectedNewRoute() {
        boolean copyOfSelectedNewRoute = selectedNewRoute;

        // turn off flag!
        selectedNewRoute = false;

        return copyOfSelectedNewRoute;  // return the value that was in th flag before reseting it
    }

    /**
     * Returns the route associated with the given out-neighbor. If the out-neighbor does not exist in
     * the table it will be returned invalid route.
     *
     * @param neighbor out-neighbor to get route.
     * @return route associated with the given neighbor.
     */
    public Route getRoute(Router neighbor) {
        Route route = routes.get(neighbor);

        return route == null ? invalidRoute() : route;
    }

    /**
     * Returns the currently selected route. If ignoredOutLink is not null it will select the best route
     * associated with any out-neighbor exception the ignoredOutLink.
     *
     * @param ignoredNeighbor out-neighbor to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getAlternativeRoute(Router ignoredNeighbor) {

        if (ignoredNeighbor == null || !ignoredNeighbor.equals(selectedNeighbour)) {
            return selectedRoute;
        } else {
            return getBestRoute(ignoredNeighbor);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteTable that = (RouteTable) o;

        return routes != null ? routes.equals(that.routes) : that.routes == null;

    }

    @Override
    public int hashCode() {
        return routes != null ? routes.hashCode() : 0;
    }

    @Override
    public String toString() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        getPrintableTable().printTable(new PrintStream(os), 0);

        return os.toString();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


    private TextTable getPrintableTable() {
        String[] columns = {"Neighbors", "Routes"};

        // create table with N rows and 2 columns
        // N is the number of neighbors
        String[][] table = new String[routes.size()][2];

        final Iterator<Integer> entryIndex = IntStream.range(0, routes.size()).iterator();
        routes.entrySet().forEach(entry -> {
            Integer index = entryIndex.next();
            table[index][0] = entry.getKey().toString();
            table[index][1] = entry.getValue().toString();
        });

        return new TextTable(columns, table);
    }

    /**
     * Returns the currently best route. If ignoredOutLink is not null it will return the best route associated
     * with any out-neighbor exception the ignoredOutLink.
     *
     * @param ignoredNeighbour out-neighbor to be ignored.
     * @return currently best route for the destination.
     */
    private Route getBestRoute(Router ignoredNeighbour) {
        Route bestRoute = null;

        for (Router neighbour : routes.keySet()) {
            Route route = getRoute(neighbour);

            if (!neighbour.equals(ignoredNeighbour) && (bestRoute == null || bestRoute.compareTo(route) > 0)) {
                bestRoute = route;
            }
        }

        return bestRoute;
    }

    /**
     * Selects the neighbor with the best route. It updates the selected route and the selected neighbor if
     * necessary.
     */
    private void reselect() {

        selectedRoute = invalidRoute();
        selectedNeighbour = null;
        for (Router neighbour : routes.keySet()) {
            Route neighbourRoute = getRoute(neighbour);

            if (selectedRoute.compareTo(neighbourRoute) > 0) {
                // update
                selectedRoute = neighbourRoute;
                selectedNeighbour = neighbour;
            }
        }

    }

}
