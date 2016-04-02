package simulation;

import network.Node;

import java.util.Collections;
import java.util.LinkedHashSet;

public class PathAttribute implements Attribute {

    private static final PathAttribute INVALID;

    /**
     * Initializes the INVALID
     */
    static {
        PathAttribute path = new PathAttribute();
        path.path = null;
        INVALID = path;
    }

    private LinkedHashSet<Node> path;   // must be a LinkedHashSet in order to preserve insertion order

    /**
     * Constructs an empty path.
     */
    public PathAttribute() {
        this.path = new LinkedHashSet<>();
    }

    public PathAttribute(Node node) {
        this.path = new LinkedHashSet<>(1);
        path.add(node);
    }

    public PathAttribute(Node[] nodes) {
        this.path = new LinkedHashSet<>();
        Collections.addAll(path, nodes);
    }

    /**
     * Copy constructor.
     * @param path path to be copied.
     */
    public PathAttribute(PathAttribute path) {
        if (!path.isInvalid())
            this.path = new LinkedHashSet<>(path.path);
    }

    /**
     * Returns an invalid path instance.
     * @return invalid path instance.
     */
    public static PathAttribute createInvalidPath() {
        return INVALID;
    }
    /**
     * Returns an invalid path instance.
     * @return invalid path instance.
     */
    @Override
    public PathAttribute createInvalid() {
        return INVALID;
    }

    /**
     * Adds a new node to the path. If the node already exists in the path then it will no be added.
     * @param node node to be added to the path.
     */
    public void add(Node node) {
        path.add(node);
    }

    public boolean contains(Node node) {
        return !isInvalid() && path.contains(node);
    }

    @Override
    public boolean isInvalid() {
        return path == null;
    }

    /**
     * Compares the path to another path. The comparison only takes into account the number of nodes in the path
     * the nodes it contains are not taken into account.
     * @param attribute path attribute to be compared with.
     * @return a negative integer, zero, or a positive integer as this path is less than, equal to or greater than
     * the specified object.
     */
    @Override
    public int compareTo(Attribute attribute) {
        PathAttribute other = (PathAttribute) attribute;
        return this.path.size() - other.path.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathAttribute that = (PathAttribute) o;

        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }

    @Override
    public String toString() {
        if (isInvalid()) {
            return "Path[•]";
        } else {
            return "Path" + path;
        }
    }
}
