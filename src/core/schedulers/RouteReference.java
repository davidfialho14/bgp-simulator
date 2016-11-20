package core.schedulers;

import core.Route;

/**
 * This class is just a simple reference, which allows the route which references to, to be changed.
 * This is used by the exporters to modify the route of a message that as already been put in the scheduler.
 */
public class RouteReference {

    private Route route;

    public RouteReference(Route route) {
        this.route = route;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}

