package v2.core.events;


import v2.core.Link;
import v2.core.Route;
import v2.core.Router;

/**
 * Events generated when a router exports a new route.
 */
public class ExportEvent extends AbstractSimulationEvent {

    private final Route route;  // route exported
    private final Link link;    // link through which the route was exported

    /**
     * Constructs a new export event.
     *
     * @param exportLink link from which the new route was exported.
     * @param exportedRoute route exported.
     */
    public ExportEvent(int time, Link exportLink, Route exportedRoute) {
        super(time);
        this.link = exportLink;
        this.route = exportedRoute;
    }

    /**
     * Returns the link from which the new route was exported.
     *
     * @return link from which the new route was exported.
     */
    public Link getLink() {
        return link;
    }

    /**
     * Returns the route exported.
     *
     * @return route exported.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Returns the router that exported the route.
     *
     * @return router that exported the route
     */
    public Router getExportingRouter() {
        return link.getTarget();
    }

    /**
     * Returns the router that exported the route.
     *
     * @return router that exported the route
     */
    public Router getLearningRouter() {
        return link.getSource();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExportEvent event = (ExportEvent) o;

        if (link != null ? !link.equals(event.link) : event.link != null) return false;
        return route != null ? route.equals(event.route) : event.route == null;

    }

    @Override
    public int hashCode() {
        int result = link != null ? link.hashCode() : 0;
        result = 31 * result + (route != null ? route.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExportEvent{" + link + ", " + route + '}';
    }
}
