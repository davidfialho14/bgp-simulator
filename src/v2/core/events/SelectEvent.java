package v2.core.events;

import v2.core.Route;
import v2.core.Router;

/**
 * Events generated when a router selects a NEW route.
 */
public class SelectEvent extends AbstractSimulationEvent {

    private final Router selectingRouter;
    private final Route previousRoute;  // route previously selected
    private final Route selectedRoute;  // route selected

    /**
     * Constructs a new select event. The previous route and selected route should not be the same.
     *
     * @param selectingRouter router that selected route.
     * @param previousRoute previous selected route.
     * @param selectedRoute selected route.
     */
    public SelectEvent(long time, Router selectingRouter, Route previousRoute, Route selectedRoute) {
        super(time);
        this.selectingRouter = selectingRouter;
        this.previousRoute = previousRoute;
        this.selectedRoute = selectedRoute;
    }

    /**
     * Returns the router which selected a new route.
     *
     * @return the router which selected a new route.
     */
    public Router getSelectingRouter() {
        return selectingRouter;
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
