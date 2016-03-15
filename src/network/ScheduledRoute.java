package network;

/**
 * Implements the representation of a route that has been scheduled. It associates the exportation link and a
 * timestamp with the route. All scheduled route instances are read only outside the package.
 */
public class ScheduledRoute implements Comparable<ScheduledRoute> {

    private Route route;
    private Link link;
    private long timestamp;

    public ScheduledRoute(Route route, Link link, long timestamp) {
        this.route = route;
        this.link = link;
        this.timestamp = timestamp;
    }

    public Route getRoute() {
        return route;
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

}

