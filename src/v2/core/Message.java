package v2.core;

import v2.core.schedulers.RouteReference;

/**
 * Message is an abstraction of a BGP message. A message contains the link trough which is sent, the route,
 * and the time of arrival to the the source node of the link. Messages are always sent from the target
 * node to the source node.
 */
public class Message {

    private int arrivalTime;
    private final Link traversedLink;
    private final RouteReference routeReference;

    public Message(int time, Link traversedLink, RouteReference routeReference) {
        this.arrivalTime = time;
        this.traversedLink = traversedLink;
        this.routeReference = routeReference;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public Link getTraversedLink() {
        return traversedLink;
    }

    public Route getRoute() {
        return routeReference.getRoute();
    }

    /**
     * Sets the arrival time of the message.
     *
     * @param time the time value to set as the arrival time of the message.
     */
    public void setArrivalTime(int time) {
        this.arrivalTime = time;
    }

}
