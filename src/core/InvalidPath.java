package core;

import core.topology.ConnectedNode;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Represents an invalid path. It is implemented as a singleton meaning it has only one instance every time.
 * When compared with any other path the invalid path is always less preferable.
 */
public class InvalidPath extends Path {

    // It exists only one unique Invalid Path instance that can be accessed through the
    // static method invalidPath().
    private static final InvalidPath invalid = new InvalidPath();

    private InvalidPath() {
    } // not be instantiated directly

    /**
     * Returns always the same instance of an invalid path. Its the only way to get an invalid path
     * instance.
     *
     * @return invalid path instance.
     */
    public static InvalidPath invalidPath() {
        return invalid;
    }

    /**
     * Invalid path never contains any node
     *
     * @param node node to check if the path contains.
     * @return always false.
     */
    @Override
    public boolean contains(ConnectedNode node) {
        return false;
    }

    /**
     * The invalid path is equal to other invalid paths and greater than any other path.
     *
     * @param path path to be compared.
     * @return 0 if path is invalid or greater than 0 if is not an invalid path.
     */
    @Override
    public int compareTo(Path path) {
        if (path instanceof InvalidPath) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof InvalidPath;
    }

    // HASHCODE - Since there is only one object the default hashCode() implementation is sufficient

    @Override
    public String toString() {
        return "[•]";
    }

    // Path's Unsupported Operations

    @Override
    public void add(ConnectedNode node) {
        throw new UnsupportedOperationException("Can not add nodes to an invalid path");
    }

    @Override
    public Path getPathAfter(ConnectedNode node) {
        throw new UnsupportedOperationException("Can not get any sub-path of an invalid path");
    }

    @Override
    public Path getSubPathBefore(ConnectedNode endingNode) {
        throw new UnsupportedOperationException("Can not get any sub-path of an invalid path");
    }

    @Override
    public Iterator<ConnectedNode> iterator() {
        throw new UnsupportedOperationException("Can not iterate over an invalid path");
    }

    @Override
    public Stream<ConnectedNode> stream() {
        throw new UnsupportedOperationException("There is not stream for an invalid path");
    }
}
