package network;

public class Route implements Comparable<Route> {

    private Node destination;
    private Attribute attribute;
    private PathAttribute path;

    public Route(Node destination, Attribute attribute, PathAttribute path) {
        this.destination = destination;
        this.attribute = attribute;
        this.path = path;
    }

    public static Route createInvalid(Node destination, AttributeFactory factory) {
        return new Route(destination, factory.createInvalid(), PathAttribute.createInvalid());
    }

    /**
     * Checks if the route is invalid. A route is considered invalid if the attribute or the path are invalid.
     * @return true if the route is invalid and false otherwise.
     */
    public boolean isInvalid() {
        return attribute.isInvalid() || path.isInvalid();
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public void setPath(PathAttribute path) {
        this.path = path;
    }

    public Node getDestination() {
        return destination;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public PathAttribute getPath() {
        return path;
    }

    @Override
    public int compareTo(Route other) {
        if (this.isInvalid() && other.isInvalid()) return 0;
        else if (!this.isInvalid() && other.isInvalid()) return -1;
        else if (this.isInvalid() && !other.isInvalid()) return 1;

        int attrComparison = this.attribute.compareTo(other.attribute);
        if (attrComparison == 0)
            return path.compareTo(other.path);
        else
            return attrComparison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (destination != null ? !destination.equals(route.destination) : route.destination != null) return false;
        if (attribute != null ? !attribute.equals(route.attribute) : route.attribute != null) return false;
        return path != null ? path.equals(route.path) : route.path == null;

    }

    @Override
    public int hashCode() {
        int result = destination != null ? destination.hashCode() : 0;
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Route(" + destination +
                       ", " + attribute +
                       ", " + path + ')';
    }
}
