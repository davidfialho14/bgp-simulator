package core;

import core.topology.ConnectedNode;


/**
 * An unlabelled link is a link without label, which means the link only represents the connection between
 * two nodes and does not contain any information about the relationship of this nodes.
 */
public class UnlabelledLink {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final ConnectedNode source;
    private final ConnectedNode destination;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a link connecting the source to the destination.
     *
     * @param source source node of the link
     * @param destination destination node of the link
     */
    public UnlabelledLink(ConnectedNode source, ConnectedNode destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Creates a link connecting two nodes with the given source and destination ids.
     *
     * @param srcId id of the source node of the link
     * @param destId id of the destination node of the link
     */
    public UnlabelledLink(int srcId, int destId) {
        this.source = new ConnectedNode(srcId);
        this.destination = new ConnectedNode(destId);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the source node of the link.
     *
     * @return the source node of the link
     */
    public ConnectedNode getSource() {
        return this.source;
    }

    /**
     * Returns the destination node of the link.
     *
     * @return the destination node of the link
     */
    public ConnectedNode getDestination() {
        return this.destination;
    }

    /**
     * Two links are considered equal if they share the same source and destination nodes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnlabelledLink that = (UnlabelledLink) o;

        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        return destination != null ? destination.equals(that.destination) : that.destination == null;

    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return source + "->" + destination;
    }

}
