package core.events;


import core.Link;
import core.Route;
import core.Router;

/**
 * Events generated when a router imports a route.
 */
public class ImportEvent extends AbstractSimulationEvent {

    private Route route;    // imported route
    private Link link;      // link from which the route was imported

    /**
     * Constructs a new import event.
     *
     * @param importedRoute route imported.
     * @param importLink link from which the route was imported.
     */
    public ImportEvent(int time, Route importedRoute, Link importLink) {
        super(time);
        this.route = importedRoute;
        this.link = importLink;
    }

    /**
     * Returns the imported route.
     *
     * @return the imported route.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Returns the link from which the route was imported.
     *
     * @return the link from which the route was imported.
     */
    public Link getLink() {
        return link;
    }

    /**
     * Returns the router that imported the route.
     *
     * @return router that imported the route
     */
    public Router getImportingRouter() {
        return link.getSource();
    }

    /**
     * Returns the router that exported the route.
     *
     * @return router that exported the route
     */
    public Router getExportingRouter() {
        return link.getTarget();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImportEvent that = (ImportEvent) o;

        if (route != null ? !route.equals(that.route) : that.route != null) return false;
        return link != null ? link.equals(that.link) : that.link == null;

    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImportEvent{" + route + ", " + link + '}';
    }
}
