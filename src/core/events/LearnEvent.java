package core.events;

import core.Link;
import core.Route;
import core.Router;

/**
 * Events generated when a router learns a new route.
 */
public class LearnEvent extends AbstractSimulationEvent {

    private final Link link;    // link from which the route was learned
    private final Route route;  // route learned

    /**
     * Constructs a new learn event.
     *
     * @param learnLink link from which the new route was learned.
     * @param learnedRoute route learned.
     */
    public LearnEvent(int time, Link learnLink, Route learnedRoute) {
        super(time);
        this.link = learnLink;
        this.route = learnedRoute;
    }

    /**
     * Returns the link from which the new route was learned.
     *
     * @return link from which the new route was learned.
     */
    public Link getLink() {
        return link;
    }

    /**
     * Returns the route learned.
     *
     * @return route learned.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Returns the router that learned the route.
     *
     * @return router that learned the route
     */
    public Router getLearningRouter() {
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

        LearnEvent event = (LearnEvent) o;

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
        return "LearnEvent{" + link + ", " + route + '}';
    }
}
