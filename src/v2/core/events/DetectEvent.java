package v2.core.events;


import v2.core.Link;
import v2.core.Path;
import v2.core.Route;
import v2.core.Router;

/**
 * Events generated when a router learns a new route.
 */
public class DetectEvent extends AbstractSimulationEvent {

    private final Link outLink;             // out-link from which the route was learned
    private final Route learnedRoute;       // route learned
    private final Route alternativeRoute;   // alternative route

    /**
     * Constructs a new detect event.
     *
     * @param outLink out link from which the route was learned.
     * @param learnedRoute route learned.
     * @param alternativeRoute alternative route.
     */
    public DetectEvent(int time, Link outLink, Route learnedRoute, Route alternativeRoute) {
        super(time);
        this.outLink = outLink;
        this.learnedRoute = learnedRoute;
        this.alternativeRoute = alternativeRoute;
    }

    /**
     * Gets the cycle that originated a detect event.
     *
     * @return cycle that originated detect event.
     */
    public Path getCycle() {
        Path cycle = getLearnedRoute().getPath().getSubPathBefore(getDetectingRouter());
        cycle.add(getDetectingRouter());    // include the detecting router in the start and end of the cycle path

        return cycle;
    }

    /**
     * Returns the out-link from which the new route was learned.
     *
     * @return out-link from which the new route was learned.
     */
    public Link getOutLink() {
        return outLink;
    }

    /**
     * Returns the route learned.
     *
     * @return route learned.
     */
    public Route getLearnedRoute() {
        return learnedRoute;
    }

    /**
     * Returns the alternative route.
     *
     * @return alternative route.
     */
    public Route getAlternativeRoute() {
        return alternativeRoute;
    }

    /**
     * Returns the router that detected.
     *
     * @return router that detected.
     */
    public Router getDetectingRouter() {
        return outLink.getSource();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetectEvent that = (DetectEvent) o;

        if (outLink != null ? !outLink.equals(that.outLink) : that.outLink != null) return false;
        if (learnedRoute != null ? !learnedRoute.equals(that.learnedRoute) : that.learnedRoute != null) return false;
        return alternativeRoute != null ? alternativeRoute.equals(that.alternativeRoute) : that.alternativeRoute == null;

    }

    @Override
    public int hashCode() {
        int result = outLink != null ? outLink.hashCode() : 0;
        result = 31 * result + (learnedRoute != null ? learnedRoute.hashCode() : 0);
        result = 31 * result + (alternativeRoute != null ? alternativeRoute.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DetectEvent{" + outLink +
                ", learned=" + learnedRoute +
                ", excl=" + alternativeRoute +
                '}';
    }
}
