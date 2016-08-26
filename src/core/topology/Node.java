package core.topology;

/**
 * A node is one of the two fundamental elements of a topology. Nodes are identified by an ID which must be unique for
 * each node. Nodes with the same ID represent the same node (are equal). This must be a valid to all node
 * implementations.
 */
public interface Node {

    /**
     * Returns the node's ID.
     *
     * @return the node's ID.
     */
    int getId();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Equals and hashCode are put here to enforce they must be overridden.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Two nodes are equal if and only if they have the same id.
     */
    boolean equals(Object o);

    /**
     * Hash code follows the equals implementation.
     */
    int hashCode();

    /**
     * Default equals implementation that should be used by the subclasses. Checks if the nodes IDs are the same.
     *
     * @param node  node calling the equals method.
     * @param other other node being verified for equality.
     * @return true if they have the same ID and false otherwise.
     */
    static boolean equals(Node node, Object other) {
        if (!(other instanceof Node)) return false;

        ConnectedNode otherNode = (ConnectedNode) other;

        return node.getId() == otherNode.getId();
    }

    /**
     * Default hash code implementation that should be used by subclasses. Uses the node's Id as hashcode.
     *
     * @param node node to get hash code for.
     * @return node's ID.
     */
    static int hashCode(Node node) {
        return node.getId();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Static factory methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a simple unconnected node. Use when the connections are not necessary.
     *
     * @param id id to assign to the new node
     * @return new node instance.
     */
    static Node newNode(int id) {
        return new BaseNode(id);
    }

}
