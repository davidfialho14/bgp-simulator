package network;

import simulation.Attribute;
import simulation.Label;

public class Link {

	private Node source;
	private Node destination;
    private Label label;

	/**
	 * Creates a link connecting the source to the destination.
	 * @param source source node of the link.
	 * @param destination destination node of the link.
     * @param label label associated with the link.
	 */
	public Link(Node source, Node destination, Label label) {
		this.source = source;
        this.destination = destination;
        this.label = label;
	}

	/**
	 * Returns the source node of the link.
	 * @return the source node of the link.
     */
	public Node getSource() {
		return this.source;
	}

	/**
	 * Returns the destination node of the link.
	 * @return the destination node of the link.
	 */
	public Node getDestination() {
		return this.destination;
	}

    /**
     * Returns the label associated with the link.
     * @return the label associated with the link.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Extends the given attribute.
     * @param attribute attribute to be extended.
     * @return extended attribute.
     */
    public Attribute extend(Attribute attribute) {
        return label.extend(this, attribute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (source != null ? !source.equals(link.source) : link.source != null) return false;
        if (destination != null ? !destination.equals(link.destination) : link.destination != null) return false;
        return label != null ? label.equals(link.label) : link.label == null;

    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link(" + source + "->" + destination + ", " + label + ')';
    }
}