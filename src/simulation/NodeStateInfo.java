package simulation;

import network.Link;
import network.Node;
import policies.Attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregates all the state information of one node in one unique class
 */
public class NodeStateInfo {

    private RouteTable table;
    private Map<Node, Route> selectedRoutes = new HashMap<>();

    public NodeStateInfo(Node node) {
        this.table = new RouteTable(node.getOutLinks());
    }

    public RouteTable getTable() {
        return table;
    }

    /**
     * Returns a map between the destinations and the respective selected routes
     * @return a map between the destinations and the respective selected routes.
     */
    public Map<Node, Route> getSelectedRoutes() {
        return selectedRoutes;
    }

    /**
     * Returns the currently selected attribute for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the attribute for.
     * @return currently selected attribute or null if the destination was not known.
     */
    public Attribute getSelectedAttribute(Node destination) {
        Route selectedRoute = selectedRoutes.get(destination);
        return selectedRoute != null ? selectedRoute.getAttribute() : null;
    }

    /**
     * Returns the currently selected path for the given destination. If the destination was not yet known it
     * returns null.
     * @param destination destination to get the path for.
     * @return currently selected path or null if the destination was not known.
     */
    public PathAttribute getSelectedPath(Node destination) {
        Route selectedRoute = selectedRoutes.get(destination);
        return selectedRoute != null ? selectedRoute.getPath() : null;
    }

    /**
     * Returns the currently selected route for the given destination. If the ignored link is specified it selects
     * the preferred route excluding the route learned from that out-link.
     *
     * @param destination destination to get selected route for.
     * @param ignoredLink out-link to be ignored.
     * @return currently selected route.
     */
    public Route getSelectedRoute(Node destination, Link ignoredLink) {
        return table.getSelectedRoute(destination, ignoredLink);
    }

    /**
     * Sets the given route as the currently selected route for the destination.
     *
     * @param destination destination to set route for.
     * @param route route to set
     * @return currently selected route.
     */
    public void setSelected(Node destination, Route route) {
        selectedRoutes.put(destination, route);
    }

    /**
     * Updates the attribute and/or path of the route for the given destination and out-link.
     *
     * @param destination destination to update.
     * @param outLink out-link to update.
     * @param attribute attribute to update route to.
     * @param path path to update to.
     */
    public void updateRoute(Node destination, Link outLink, Attribute attribute, PathAttribute path) {
        table.setRoute(outLink, new Route(destination, attribute, path));
    }

    public void updateRoute(Link outLink, Route route) {
        table.setRoute(outLink, route);
    }
}
