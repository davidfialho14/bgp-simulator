package network;


public abstract class Node {

    private Network network;	// network who created the node

    protected int id;   // id must be unique in each network

	/**
     * @param network   network who created the node.
	 * @param id    id to assign to the node.
	 */
    protected Node(Network network, int id) {
		this.network = network;
		this.id = id;
	}

    /**
     * Returns the id of the node.
     * @return id of the node.
     */
	public int getId() {
		return this.id;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        return network != null ? network.equals(node.network) : node.network == null;

    }

    @Override
    public int hashCode() {
        int result = network != null ? network.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}