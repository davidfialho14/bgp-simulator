package network;

import java.util.LinkedHashSet;

public class PathAttribute implements Attribute {

    private static PathAttribute invalid() {
        PathAttribute path = new PathAttribute();
        path.path = null;
        return path;
    }

    private static final PathAttribute INVALID = invalid();

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

    /**
     * Copy constructor.
     * @param path path to be copied.
     */
    public PathAttribute(PathAttribute path) {
        this.path = new LinkedHashSet<>(path.path);
    }

    /**
     * Returns an invalid path instance.
     * @return invalid path instance.
     */
    public static PathAttribute createInvalid() {
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
        // TODO - implement PathAttribute.contains
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInvalid() {
        return path == null;
    }

    @Override
    public int compareTo(Attribute attribute) {
        PathAttribute other = (PathAttribute) attribute;
        int comparison = this.path.size() - other.path.size();

        if (comparison == 0) {
            if(this.path.equals(other.path)) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return comparison;
        }
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
}
