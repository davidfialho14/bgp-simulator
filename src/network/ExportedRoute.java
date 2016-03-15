package network;

/**
 * Associates a link and a route. This class is to be used for the scheduler only.
 */
public class ExportedRoute {

    private Link link;
    private Route route;

    public ExportedRoute(Link link, Route route) {
        this.link = link;
        this.route = route;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
