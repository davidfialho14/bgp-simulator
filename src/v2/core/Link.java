package v2.core;

/**
 * A link is one of the two fundamental elements of a topology. Links are directed and connect a source
 * router and a target router. Each link is associated with a label modelling the relationship between the
 * two routers. A link is an abstraction of a real link between two routers, therefore, it stores some
 * state such as the last time a message was sent through the link and it can be turned on and off (this
 * models turning a link on/off by software in a real router).
 */
public class Link extends DirectedEdge {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Label label;

    // when active routes exported through this link are considered invalid
    // this flag is checked by the protocol in the "import" phase.
    // by default all links are turned on
    private boolean turnedOff = false;

    // stores the arrival time of the last message sent through this link
    private int lastArrivalTime = 0;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/**
	 * Creates a link connecting the source to the target.
     * 
	 * @param source source node of the link.
	 * @param target target node of the link.
     * @param label label associated with the link.
	 */
	public Link(Router source, Router target, Label label) {
		super(source, target);
        this.label = label;
	}

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the label associated with the link.
     * @return the label associated with the link.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Checks if the link is turned off.
     *
     * @return true if the link is turned off and false otherwise.
     */
    public boolean isTurnedOff() {
        return turnedOff;
    }

    /**
     * Turns on or off the link.
     *
     * @param turnedOff true to turn off and false to turn on.
     */
    public void setTurnedOff(boolean turnedOff) {
        this.turnedOff = turnedOff;
    }

    /**
     * Returns the link's last arrival time. Corresponds the to arrival time of the last message sent
     * through this link.
     *
     * @return the link's last arrival time.
     */
    public int getLastArrivalTime() {
        return lastArrivalTime;
    }

    /**
     * Sets a new value for the link's last arrival time.
     *
     * @param lastArrivalTime value to set as the link's last arrival time.
     */
    public void setLastArrivalTime(int lastArrivalTime) {
        this.lastArrivalTime = lastArrivalTime;
    }

    /**
     * Two links are equal if they share the same source and target routers and have the same label.
     *
     * @param other router to compare to.
     * @return true this router is equal to the other or false if not.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        if (!super.equals(other)) return false;

        Link link = (Link) other;

        return label != null ? label.equals(link.label) : link.label == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link(" + label + ", " + target + "<-" + source + ')';
    }

}