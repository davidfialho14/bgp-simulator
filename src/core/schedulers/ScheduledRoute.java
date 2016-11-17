package core.schedulers;

import core.Route;
import core.topology.Link;

/**
 * Implements the representation of a routeReference that has been scheduled. It associates the exportation link and a
 * timestamp with the routeReference. All scheduled routeReference instances are read only outside the package.
 */
public class ScheduledRoute implements Comparable<ScheduledRoute> {

    private RouteReference routeReference;
    private Link link;
    private long timestamp;

    public ScheduledRoute(RouteReference route, Link link, long timestamp) {
        this.routeReference = route;
        this.link = link;
        this.timestamp = timestamp;
    }

    public Route getRoute() {
        return routeReference.getRoute();
    }

    public Link getLink() {
        return link;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(ScheduledRoute scheduledRoute) {
        return (int) (this.timestamp - scheduledRoute.timestamp);
    }

    @Override
    public String toString() {
        return "SR(" + timestamp +
                ", " + routeReference +
                ", " + link +
                ")";
    }
}
