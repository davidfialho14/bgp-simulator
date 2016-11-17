package v2.core;

/**
 * Message is an abstraction of a BGP message. A message contains the link trough which is sent, the route,
 * and the time of arrival to the the source node of the link. Messages are always sent from the target
 * node to the source node.
 */
public class Message {

    private final int arrivalTime;
    private final Link traversedLink;
    private final Route route;

    public Message(int time, Link traversedLink, Route route) {
        this.arrivalTime = time;
        this.traversedLink = traversedLink;
        this.route = route;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public Link getTraversedLink() {
        return traversedLink;
    }

    public Route getRoute() {
        return route;
    }
    
}
