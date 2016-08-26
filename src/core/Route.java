package core;

import core.topology.Node;
import core.topology.Policy;

import java.util.HashMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static core.InvalidPath.invalidPath;

/**
 * Model the routes exchanged between the nodes during the protocol. Routes associate a destination node with
 * a policy attribute and a path to that same destination.
 */
public class Route implements Comparable<Route> {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Node destination;
    private Attribute attribute;
    private Path path;

    private final Map<Node, Attribute> selectedAttributes;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Copy constructor. Only the path attribute is hard copied, the destination node and attribute are both
     * only copied by reference.
     *
     * @param route route to be copied.
     */
    public Route(Route route) {
        this.destination = route.destination;
        this.attribute = route.attribute;
        this.path = Path.copy(route.path);
        this.selectedAttributes = new HashMap<>(route.selectedAttributes);
    }

    /**
     * Constructs a new route given the necessary fields. Must be private because it show some implementation details
     * like the container used to store selected attributes.
     *
     * @param destination destination of the route.
     * @param attribute policy attribute of the route.
     * @param path path to reach the destination.
     * @param selectedAttributes map with the selected attributes
     */
    private Route(Node destination, Attribute attribute, Path path, Map<Node, Attribute> selectedAttributes) {
        this.destination = destination;
        this.attribute = attribute;
        this.path = path;
        this.selectedAttributes = selectedAttributes;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Creation and Modification Methods.
     *
     *  Routes are created and modified using a builder class.
     *  A route is created by modifying the attribute and/or path of another route. The created
     *  route is always a new Route instance and never the original route modified.
     *
     *  To create a new route the #newRouteFrom() should be called and then the new attribute and/or
     *  path should be specified. For example:
     *
     *  newRouteFrom(original)
     *      .withAttribute(attribute)
     *      .build()
     *
     *  In this case I only specified the new attribute, which means that the path will be the
     *  same as the original. To mark the attribute as a new selected attribute the #selectedBy()
     *  build method should be used. For example:
     *
     *  newRouteFrom(original)
     *      .withAttribute(attribute)
     *      .selectedBy(node)
     *      .build()
     *
     *  Routes are always created from an original route, except invalid routes and self routes.
     *  To create those type of routes used the respective factory methods.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new invalid route.
     *
     * @param destination destination of the route.
     * @return new invalid Route instance.
     */
    public static Route invalidRoute(Node destination) {
        return new Route(destination, invalidAttr(), invalidPath(), new HashMap<>());
    }

    /**
     * Creates a new self route for the given node.
     *
     * @param node node to create self route for.
     * @param policy policy used to create a self attribute to the given node.
     * @return new self Route instance to the given node.
     */
    public static Route createSelf(Node node, Policy policy) {
        return new Route(node, policy.createSelf(node), new Path(), new HashMap<>());
    }

    /**
     * Starts the process to create a new route from another.
     *
     * @param originalRoute original route to start with.
     * @return a builder for the new Route.
     */
    public static Builder newRouteFrom(Route originalRoute) {
        return new Builder(originalRoute);
    }

    public static class Builder {

        private final Route originalRoute;

        private Attribute attribute = null;
        private Node selectingNode = null;
        private Path path = null;

        public Builder(Route originalRoute) {
            this.originalRoute = originalRoute;
        }

        public Builder withAttribute(Attribute attribute) {
            this.attribute = attribute;
            return this;
        }

        public Builder selectedBy(Node node) {
            this.selectingNode = node;
            return this;
        }

        public Builder withPath(Path path) {
            this.path = path;
            return this;
        }

        public Route build() {

            if (attribute == null) {
                attribute = originalRoute.getAttribute();
            } else if (path == null) {
                path = Path.copy(originalRoute.path);
            }

            // copy the original route's selected attributes
            Map<Node, Attribute> selectedAttributes = new HashMap<>(originalRoute.selectedAttributes);

            if (selectingNode != null) {
                selectedAttributes.put(selectingNode, attribute);
            }

            return new Route(originalRoute.destination, attribute, path, selectedAttributes);
        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the route's destination.
     *
     * @return destination of the route.
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Assigns the given destination to the route.
     *
     * @param destination node to be assigned as destination.
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    /**
     * Returns the route's attribute.
     *
     * @return attribute of the route.
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Assigns the given attribute to the route.
     *
     * @param attribute attribute to be assigned.
     */
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    /**
     * Returns the route's path.
     *
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
     *
     * @return true if the route is invalid and false otherwise.
     */
    public boolean isInvalid() {
        return attribute == invalidAttr() || path == invalidPath();
    }

    /**
     * Compares this route with another route. The comparison considers the attribute order first and second the path
     * order.
     *
     * @param other other route to compare to.
     * @return 0 if they are equal, -1 if this route is preferable and 1 if the other route is preferable.
     */
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
        if (!(o instanceof Route)) return false;

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
