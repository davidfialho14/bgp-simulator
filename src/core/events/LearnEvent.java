package core.events;

import core.Route;
import core.topology.Link;
import core.topology.Node;

/**
 * Events generated when a node learns a new route.
 */
public class LearnEvent implements SimulationEvent {

    private final Link link;    // link from which the route was learned
    private final Route route;  // route learned

    /**
     * Constructs a new learn event.
     *
     * @param learnLink link from which the new route was learned.
     * @param learnedRoute route learned.
     */
    public LearnEvent(Link learnLink, Route learnedRoute) {
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
     * Returns the node that learned the route.
     *
     * @return node that learned the route
     */
    public Node getLearningNode() {
        return link.getSource();
    }

    /**
     * Returns the node that exported the route.
     *
     * @return node that exported the route
     */
    public Node getExportingNode() {
        return link.getDestination();
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
