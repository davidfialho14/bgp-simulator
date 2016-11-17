package core;

import core.topology.ConnectedNode;
import core.topology.Link;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

import static core.InvalidPath.invalidPath;

/**
 * Represents a path to a destination
 */
public class Path implements Comparable<Path>, Iterable<ConnectedNode> {

    private LinkedList<ConnectedNode> path = null;   // must be a LinkedList in order to preserve insertion order

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
    public Path(ConnectedNode node) {
        this.path = new LinkedList<>();
        path.add(node);
    }

    /**
     * Constructs a path given a sequence of nodes. Nodes are added to the path in the same order they are stored
     * in the array.
     * @param nodes nodes to initiate the path with.
     */
    public Path(ConnectedNode[] nodes) {
        this.path = new LinkedList<>();
        Collections.addAll(path, nodes);
    }

    /**
     * Only to be used to create path internally.
     */
    private Path(LinkedList<ConnectedNode> path) {
        this.path = path;
    }

    /**
     * Creates a copy of the given path. If the given path is the invalid path it returns the invalid path.
     *
     * @param path path to be copied (may be an invalid path).
     * @return new copy of the given path or the invalid path if the given path is the invalid path.
     */
    public static Path copy(Path path) {
        if (path == invalidPath()) {
            return invalidPath();
        } else {
            return new Path(new LinkedList<>(path.path));
        }
    }

    /**
     * Adds a new node to the path. If the node already exists in the path then it will no be added.
     * @param node node to be added to the path.
     */
    public void add(ConnectedNode node) {
        path.addFirst(node);
    }

    /**
     * Checks if the path contains the given node.
     * @param node node to check if the path contains.
     * @return true if the path contains the node and false otherwise.
     */
    public boolean contains(ConnectedNode node) {
        return this != invalidPath() && path.contains(node);
    }

    /**
     * Returns the path after the given node. If the node does not exist an empty path is returned.
     * If the path is invalid an invalid path is returned as well.
     *
     * @param node node to get path after.
     * @return path after the node or empty path if the node is not found or invalid if the path is invalid.
     */
    public Path getPathAfter(ConnectedNode node) {
        if (this == invalidPath()) return invalidPath();

        // start with an empty path
        Path pathAfterNode = new Path();

        Iterator<ConnectedNode> nodeItr = path.iterator();

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
    public Path getSubPathBefore(ConnectedNode endingNode) {
        LinkedList<ConnectedNode> subpath = new LinkedList<>();

        for (ConnectedNode node : path) {
            subpath.add(node);

            if (node.equals(endingNode)) {
                return new Path(subpath);
            }
        }

        return null; // ending node was not found
    }

    /**
     * Returns the source node of the path. Source node is the initial node in the path.
     *
     * @return the source node of the path.
     */
    public ConnectedNode getSource() {
        return path.getFirst();
    }

    /**
     * Returns the destination node of the path. Source node is the initial node in the path.
     *
     * @return the destination node of the path.
     */
    public ConnectedNode getDestination() {
        return path.getLast();
    }

    /**
     * Compares the path to another path. The comparison only takes into account the number of nodes in the path
     * the specific nodes it contains are not taken into account.
     *
     * @param other path to be compared with.
     * @return a negative integer, zero, or a positive integer as this path is less than, equal to or greater than
     * the specified object.
     */
    @Override
    public int compareTo(Path other) {
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
        return "Path" + path;
    }

    /**
     * Returns an iterator over nodes of the path.
     *
     * @return an Iterator of nodes.
     */
    @Override
    public Iterator<ConnectedNode> iterator() {
        return path.iterator();
    }

    public Stream<ConnectedNode> stream() {
        return path.stream();
    }

    /**
     * Returns an iterator over the out-links in the path
     *
     * @return an Iterator of out-links.
     */
    public Iterator<Link> outLinksIterator() {
        return new OutLinksIterator();
    }

    private class OutLinksIterator implements Iterator<Link> {

        private Iterator<ConnectedNode> iterator = Path.this.iterator();
        private ConnectedNode currentNode = iterator.next(); // all paths have at least one node

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public Link next() {
            ConnectedNode neighbour = iterator.next();
            Link nextLink = currentNode.getOutLink(neighbour);
            currentNode = neighbour;

            return nextLink;
        }
    }

    /**
     * Returns an iterator over the in-links in the path
     *
     * @return an Iterator of in-links.
     */
    public Iterator<Link> inLinksIterator() {
        return new InLinksIterator();
    }

    private class InLinksIterator implements Iterator<Link> {

        private Iterator<ConnectedNode> iterator = Path.this.path.descendingIterator();
        private ConnectedNode currentNode = iterator.next(); // all paths have at least one node

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         */
        @Override
        public Link next() {
            ConnectedNode neighbour = iterator.next();
            Link nextLink = currentNode.getInLink(neighbour);
            currentNode = neighbour;

            return nextLink;
        }

    }

}
