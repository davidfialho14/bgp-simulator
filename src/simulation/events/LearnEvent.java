package simulation.events;

import network.Link;
import network.Node;
import simulation.Route;

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
     * Returns the node the learned the route.
     *
     * @return node that learned the route
     */
    public Node getLearningNode() {
        return link.getDestination();
    }

}
