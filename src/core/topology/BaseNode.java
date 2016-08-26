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

    @Override
    public boolean equals(Object o) {
        return Node.equals(this, o);
    }

    @Override
    public int hashCode() {
        return Node.hashCode(this);
    }

    @Override
    public String toString() {
        return "Node(" + id + ")";
    }

}
