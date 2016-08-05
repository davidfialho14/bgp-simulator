package core.events;

import core.Route;
import network.Node;

/**
 * Events generated when a node selects a route.
 */
public class SelectEvent implements SimulationEvent {

    private final Node selectingNode;
    private final Route previousRoute;  // route previously selected
    private final Route selectedRoute;  // route selected

    /**
     * Constructs a new select event. The previous route and selected route can be the same.
     *
     * @param selectingNode node that selected route.
     * @param previousRoute previous selected route.
     * @param selectedRoute selected route.
     */
    public SelectEvent(Node selectingNode, Route previousRoute, Route selectedRoute) {
        this.selectingNode = selectingNode;
        this.previousRoute = previousRoute;
        this.selectedRoute = selectedRoute;
    }

    public Node getSelectingNode() {
        return selectingNode;
    }

    /**
     * Returns previous selected route.
     *
     * @return previous selected route.
     */
    public Route getPreviousRoute() {
        return previousRoute;
    }

    /**
     * Returns selected route.
     *
     * @return selected route.
     */
    public Route getSelectedRoute() {
        return selectedRoute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectEvent that = (SelectEvent) o;

        if (previousRoute != null ? !previousRoute.equals(that.previousRoute) : that.previousRoute != null)
            return false;
        return selectedRoute != null ? selectedRoute.equals(that.selectedRoute) : that.selectedRoute == null;

    }

    @Override
    public int hashCode() {
        int result = previousRoute != null ? previousRoute.hashCode() : 0;
        result = 31 * result + (selectedRoute != null ? selectedRoute.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SelectEvent{" + previousRoute + ", " + selectedRoute + '}';
    }
}
