package simulation;

import network.Node;
import policies.Attribute;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class PathAttribute extends Attribute {

    private static final PathAttribute INVALID;

    /**
     * Initializes the INVALID
     */
    static {
        INVALID = new PathAttribute();
        INVALID.path = null;
    }

    private LinkedHashSet<Node> path;   // must be a LinkedHashSet in order to preserve insertion order

    /**
     * Constructs an empty path.
     */
    public PathAttribute() {
        this.path = new LinkedHashSet<>();
    }

    /**
     * Creates a new path with a single node.
     * @param node node initiate the path with.
     */
    public PathAttribute(Node node) {
        this.path = new LinkedHashSet<>(1);
        path.add(node);
    }

    /**
     * Constructs a path given a sequence of nodes. Nodes are added to the path in the same order they are stored
     * in the array.
     * @param nodes nodes to initiate the path with.
     */
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
    public static PathAttribute invalidPath() {
        return INVALID;
    }

    /**
     * Adds a new node to the path. If the node already exists in the path then it will no be added.
     * @param node node to be added to the path.
     */
    public void add(Node node) {
        path.add(node);
    }

    /**
     * Checks if the path contains the given node.
     * @param node node to check if the path contains.
     * @return true if the path contains the node and false otherwise.
     */
    public boolean contains(Node node) {
        return !isInvalid() && path.contains(node);
    }

    public PathAttribute getPathAfter(Node node) {
        if (isInvalid()) return invalidPath();

        PathAttribute pathAfterNode = new PathAttribute();

        Iterator<Node> nodeItr = path.iterator();
        while (nodeItr.hasNext()) {
            Node currentNode = nodeItr.next();
            if (currentNode.equals(node)) {
                // found the node
                break;
            }
        }

        while (nodeItr.hasNext()) {
            Node currentNode = nodeItr.next();
            pathAfterNode.add(currentNode);
        }

        return pathAfterNode;
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

    /**
     * Two paths are considered equal if they have the same nodes in the same order.
     */
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
