package core;

import network.Node;

import static core.InvalidAttribute.invalidAttr;
import static core.InvalidPath.invalidPath;

/**
 * Model the routes exchanged between the nodes during the protocol. Routes associate a destination node with
 * a policy attribute and a path to that same destination.
 */
public class Route implements Comparable<Route> {

    private Node destination;
    private Attribute attribute;
    private Path path;

    /**
     * Constructs a new route assigning it a destination, attribute, and path.
     * @param destination destination of the route.
     * @param attribute policy attribute of the route.
     * @param path path to reach the destination.
     */
    public Route(Node destination, Attribute attribute, Path path) {
        this.destination = destination;
        this.attribute = attribute;
        this.path = path;
    }

    /**
     * Copy constructor. Only the path attribute is hard copied, the destination node and attribute are both
     * only copied by reference.
     * @param route route to be copied.
     */
    public Route(Route route) {
        this.destination = route.destination;
        this.attribute = route.attribute;

        if (route.path == invalidPath())
            this.path = invalidPath();
        else
            this.path = new Path(route.path);
    }

    /**
     * Creates a new invalid route.
     * @param destination destination of the route.
     * @return new invalid Route instance.
     */
    public static Route invalidRoute(Node destination) {
        return new Route(destination, invalidAttr(), invalidPath());
    }

    /**
     * Creates a new self route for the given node.
     * @param node node to create self route for.
     * @param policy policy used to create a self attribute to the given node.
     * @return new self Route instance to the given node.
     */
    public static Route createSelf(Node node, Policy policy) {
        return new Route(node, policy.createSelf(node), new Path());
    }

    /**
     * Returns the route's destination.
     * @return destination of the route.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Assigns the given destination to the route.
     * @param destination node to be assigned as destination.
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    /**
     * Returns the route's attribute.
     * @return attribute of the route.
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Assigns the given attribute to the route.
     * @param attribute attribute to be assigned.
     */
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    /**
     * Returns the route's path.
     * @return path of the route.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Assigns the given path to the route.
     *
     * @param path path to be assigned.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Checks if the route is invalid. A route is considered invalid if the attribute or the path are invalid.
     * @return true if the route is invalid and false otherwise.
     */
    public boolean isInvalid() {
        return attribute == invalidAttr() || path == invalidPath();
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
