package core;


import dnl.utils.text.table.TextTable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * Stores the routes learned from each out-neighbor to reach the destination router.
 */
public class RouteTable {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Map<Node, Route> routes = new HashMap<>();

    private Route selectedRoute = InvalidRoute.invalidRoute();
    private Node selectedNeighbour = null;

    // flag to indicate if a new route was selected after the last time it is checked
    private boolean selectedNewRoute = false;

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
    public Node getSelectedNeighbour() {
        return selectedNeighbour;
    }

    /**
     * Sets the route for an out-neighbor. If the out-neighbor does not exist in the table, it will be
     * added and assigned to the new route. Calling this method may update the selected route.
     *
     * @param neighbor out-neighbor to update route for.
     * @param route route to be set.
     */
    public void setRoute(Node neighbor, Route route) {
        selectedNewRoute = false;   // mark no change by default - it is update if there is a new selection
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
     * Checks the the selected route was changed after the last update to the table.
     *
     * @return true if the route was selected after the last update to the table and false if otherwise.
     */
    public boolean selectedNewRoute() {
        return selectedNewRoute;
    }

    /**
     * Returns the route associated with the given out-neighbor. If the out-neighbor does not exist in
     * the table it will be returned invalid route.
     *
     * @param neighbor out-neighbor to get route.
     * @return route associated with the given neighbor.
     */
    public Route getRoute(Node neighbor) {
        Route route = routes.get(neighbor);

        return route == null ? InvalidRoute.invalidRoute() : route;
    }

    /**
     * Returns the currently selected route. If ignore neighbor is not null it will select the best route
     * associated with any out-neighbor exception the ignored neighbor.
     *
     * @param ignoredNeighbor out-neighbor to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getAlternativeRoute(Node ignoredNeighbor) {

        if (ignoredNeighbor == null || !ignoredNeighbor.equals(selectedNeighbour)) {
            return selectedRoute;
        } else {
            return getBestRoute(ignoredNeighbor);
        }
    }

    /**
     * Resets the route table to the initial conditions.
     */
    public void reset() {
        routes.clear();
        selectedNewRoute = false;
        selectedNeighbour = null;
        selectedRoute = InvalidRoute.invalidRoute();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteTable)) return false;

        RouteTable that = (RouteTable) o;

        return routes.equals(that.routes);

    }

    @Override
    public int hashCode() {
        return routes.hashCode();
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
     * Returns the currently best route. If ignored neighbor is not null it will return the best route
     * associated with any out-neighbor exception the ignored neighbor.
     *
     * @param ignoredNeighbour out-neighbor to be ignored.
     * @return currently best route for the destination.
     */
    private Route getBestRoute(Node ignoredNeighbour) {
        Route bestRoute = InvalidRoute.invalidRoute();

        for (Node neighbour : routes.keySet()) {
            Route route = getRoute(neighbour);

            if (!neighbour.equals(ignoredNeighbour) && bestRoute.compareTo(route) > 0) {
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

        selectedRoute = InvalidRoute.invalidRoute();
        selectedNeighbour = null;
        for (Node neighbour : routes.keySet()) {
            Route neighbourRoute = getRoute(neighbour);

            if (selectedRoute.compareTo(neighbourRoute) > 0) {
                // update
                selectedRoute = neighbourRoute;
                selectedNeighbour = neighbour;
            }
        }

    }

}
