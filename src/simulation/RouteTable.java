package simulation;

import network.Link;
import network.Node;
import policies.Attribute;

import java.util.Collection;

public class RouteTable {

    /**
     * Constructs a new empty route table. Defines the outLinks included in the route table.
     * @param outLinks out out-links of the route table.
     */
    public RouteTable(Collection<Link> outLinks) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Associates the given route to the destination and  out-link given. If the out-link does not exist this method
     * will have no effect on the table. If the destination is unknown it will be added to the table.
     *
     * @param destination destination node to associate route with.
     * @param outLink out-link to associate route with.
     * @param route route to be set.
     */
    public void setRoute(Node destination, Link outLink, Route route) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the route associated with the given destination and out-link pair. If the destination or the
     * out-link do not exist in the table it will be returned null.
     *
     * @param destination destination to get route.
     * @param outLink out-link to get route.
     * @return route associated with the given pair or null if one of them does not exist.
     */
    public Attribute getRoute(Node destination, Link outLink) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Clears all the routes and destinations from the table. It keeps the out-links.
     */
    public void clear() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the currently selected route for the given destination. If ignoredOutLink is not null it will select the
     * best route associated with any out-link exception the ignoredOutLink.
     *
     * @param destination destination node to get selected route for.
     * @param ignoredOutLink out-link to be ignored.
     * @return currently selected route for the destination.
     */
    public Route getSelectedRoute(Node destination, Link ignoredOutLink) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        // TODO implement
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        // TODO implement
        throw new UnsupportedOperationException();
    }
}
