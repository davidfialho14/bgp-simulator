package core.topology;

/**
 * base node is the most simple node implementation and represents a simple node with only an ID.
 */
public class BaseNode implements Node {

    private final int id;

    /**
     * Creates a new unconnected node with an ID.
     *
     * @param id id to assign to the node.
     */
    public BaseNode(int id) {
        this.id = id;
    }

    /**
     * Returns the node's ID.
     *
     * @return the node's ID.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Checks if the nodes IDs are the same.
     *
     * @param other other node being verified for equality.
     * @return true if they have the same ID and false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Node)) return false;

        Node otherNode = (Node) other;

        return id == otherNode.getId();
    }

    /**
     * The node's Id is hashcode.
     *
     * @return node's ID.
     */
    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node(" + id + ")";
    }

}
