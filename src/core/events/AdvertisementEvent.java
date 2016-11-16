package core.events;

import core.Route;
import core.topology.ConnectedNode;

/**
 * Events generated when a node advertises a new selected route. This event differs from the export event
 * in that advertisement events only happen once per selection. It also differs from the Select Events in
 * that, if an MRAI timer is active an advertisement event only occurs when the timer expires.
 */
public class AdvertisementEvent extends AbstractSimulationEvent {

    private final Route route;  // route advertised
    private final ConnectedNode advertisingNode;

    /**
     * Constructs a new advertisement event.
     *
     * @param advertisingNode   node which advertised the route
     * @param advertisedRoute     advertised route
     */
    public AdvertisementEvent(long time, ConnectedNode advertisingNode, Route advertisedRoute) {
        super(time);
        this.advertisingNode = advertisingNode;
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
     * Returns the node that advertised the route.
     *
     * @return node that advertised the route
     */
    public ConnectedNode getAdvertisingNode() {
        return advertisingNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdvertisementEvent that = (AdvertisementEvent) o;

        if (route != null ? !route.equals(that.route) : that.route != null) return false;
        return advertisingNode != null ? advertisingNode.equals(that.advertisingNode) : that.advertisingNode == null;

    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + (advertisingNode != null ? advertisingNode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AdvertisementEvent{" + advertisingNode + ", " + route + '}';
    }
}
