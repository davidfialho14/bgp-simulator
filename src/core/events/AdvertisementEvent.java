package core.events;


import core.Route;
import core.Router;

/**
 * Events generated when a router advertises a new selected route. This event differs from the export event
 * in that advertisement events only happen once per selection. It also differs from the Select Events in
 * that, if an MRAI timer is active an advertisement event only occurs when the timer expires.
 */
public class AdvertisementEvent extends AbstractSimulationEvent {

    private final Route route;  // route advertised
    private final Router advertisingRouter;

    /**
     * Constructs a new advertisement event.
     *
     * @param advertisingRouter   router which advertised the route
     * @param advertisedRoute     advertised route
     */
    public AdvertisementEvent(int time, Router advertisingRouter, Route advertisedRoute) {
        super(time);
        this.advertisingRouter = advertisingRouter;
        this.route = advertisedRoute;
    }

    /**
     * Returns the route advertised.
     *
     * @return route advertised.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Returns the router that advertised the route.
     *
     * @return router that advertised the route
     */
    public Router getAdvertisingRouter() {
        return advertisingRouter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvertisementEvent that = (AdvertisementEvent) o;

        if (route != null ? !route.equals(that.route) : that.route != null) return false;
        return advertisingRouter != null ? advertisingRouter.equals(that.advertisingRouter) : that.advertisingRouter == null;

    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + (advertisingRouter != null ? advertisingRouter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdvertisementEvent{" + advertisingRouter + ", " + route + '}';
    }
}
