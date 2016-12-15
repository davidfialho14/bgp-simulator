package core;

/**
 * Message is an abstraction of a BGP message. A message contains the link trough which is sent, the route,
 * and the time of arrival to the the source node of the link. Messages are always sent from the target
 * node to the source node.
 */
public class Message implements Comparable<Message> {

    private int arrivalTime;
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

    /**
     * Returns the target router of the message. This is the source router of the message's link.
     *
     * @return the target router of the message.
     */
    public Router getTarget() {
        return traversedLink.getSource();
    }

    /**
     * Sets the arrival time of the message.
     *
     * @param time the time value to set as the arrival time of the message.
     */
    public void setArrivalTime(int time) {
        this.arrivalTime = time;
    }

    /**
     * Compares both messages arrival times.
     */
    @Override
    public int compareTo(Message o) {
        return arrivalTime - o.getArrivalTime();
    }
}
