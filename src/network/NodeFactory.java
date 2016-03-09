package network;

public interface NodeFactory {

    /**
     * Creates a new node instance according to the factory type.
     * @param network network that created the node.
     * @param id id to assign to the node.
     * @return new node instance.
     */
    Node createNode(Network network, int id);

}
