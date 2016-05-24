package policies;

import network.Node;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

public class Path extends Attribute implements Iterable<Node> {

    private static final Path INVALID;

    /**
     * Initializes the INVALID
     */
    static {
        INVALID = new Path();
        INVALID.path = null;
    }

    private LinkedList<Node> path;   // must be a LinkedList in order to preserve insertion order

    /**
     * Constructs an empty path.
     */
    public Path() {
        this.path = new LinkedList<>();
    }

    /**
     * Creates a new path with a single node.
     * @param node node initiate the path with.
     */
    public Path(Node node) {
        this.path = new LinkedList<>();
        path.add(node);
    }

    /**
     * Constructs a path given a sequence of nodes. Nodes are added to the path in the same order they are stored
     * in the array.
     * @param nodes nodes to initiate the path with.
     */
    public Path(Node[] nodes) {
        this.path = new LinkedList<>();
        Collections.addAll(path, nodes);
    }

    /**
     * Copy constructor.
     * @param path path to be copied.
     */
    public Path(Path path) {
        if (!path.isInvalid())
            this.path = new LinkedList<>(path.path);
    }

    /**
     * Only to be used to create path internally.
     */
    private Path(LinkedList<Node> path) {
        this.path = path;
    }

    /**
     * Returns an invalid path instance.
     * @return invalid path instance.
     */
    public static Path invalidPath() {
        return INVALID;
    }

    /**
     * Adds a new node to the path. If the node already exists in the path then it will no be added.
     * @param node node to be added to the path.
     */
    public void add(Node node) {
        path.addFirst(node);
    }

    /**
     * Checks if the path contains the given node.
     * @param node node to check if the path contains.
     * @return true if the path contains the node and false otherwise.
     */
    public boolean contains(Node node) {
        return !isInvalid() && path.contains(node);
    }

    /**
     * Returns the path after the given node. If the node does not exist an empty path is returned.
     * If the path is invalid an invalid path is returned as well.
     *
     * @param node node to get path after.
     * @return path after the node or empty path if the node is not found or invalid if the path is invalid.
     */
    public Path getPathAfter(Node node) {
        if (isInvalid()) return invalidPath();

        // start with an empty path
        Path pathAfterNode = new Path();

        Iterator<Node> nodeItr = path.iterator();

        // start by finding in the path the node in question
        while (nodeItr.hasNext()) {
            if (nodeItr.next().equals(node)) {
                // found the node
                break;
            }
        }

        // add to the new path, all nodes in the path after the node in question
        nodeItr.forEachRemaining(pathAfterNode.path::add);

        return pathAfterNode;
    }

    /**
     * Returns the sub-path until reaching the ending node. The sub-path returned includes all node from start until
     * the ending node (inclusive). If the node is never found it returns null.

     * @param endingNode ending node.
     * @return sub-path until reaching the ending node or null if the ending node does not exist.
     */
    public Path getSubPathBefore(Node endingNode) {
        LinkedList<Node> subpath = new LinkedList<>();

        for (Node node : path) {
            subpath.add(node);

            if (node.equals(endingNode)) {
                return new Path(subpath);
            }
        }

        return null; // ending node was not found
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
        Path other = (Path) attribute;
        return this.path.size() - other.path.size();
    }

    /**
     * Two paths are considered equal if they have the same nodes in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path that = (Path) o;

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

    /**
     * Returns an iterator over nodes of the path.
     *
     * @return an Iterator of nodes.
     */
    @Override
    public Iterator<Node> iterator() {
        return path.iterator();
    }

    public Stream<Node> stream() {
        return path.stream();
    }
}
