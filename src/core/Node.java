package core;

/**
 * A node is the basic structure to model a router. It only contains an ID that must be unique for one
 * topology.
 */
public class Node {

    // the node's ID is its main identification and should be unique for every node in a topology
    protected final int id;

    /**
     * Creates a new node with an ID.
     *
     * @param id id to assign to the node.
     */
    public Node(int id) {
        this.id = id;
    }

    /**
     * Returns the node's ID.
     *
     * @return the node's ID.
     */
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
