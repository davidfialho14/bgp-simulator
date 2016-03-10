package network;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Node {

    private Network network;    // network who created the node

    private int id;   // id must be unique in each network
    private List<Node> outNeighbours = new ArrayList<>();
    private List<Link> inLinks = new ArrayList<>();

    // fields used during simulation
    private RouteTable routeTable;
    private Protocol protocol;
    private Attribute selectedAttribute;    // currently selected attribute
    private PathAttribute selectedPath;     // currently selected path

    /**
     * @param network   network who created the node.
	 * @param id    id to assign to the node.
	 */
    // TODO add a protocol to the constructor
    // TODO make the constructor public and remove the classes NodeFactory, BGPNode, BGPNodeFactory
    protected Node(Network network, int id, Protocol protocol) {
		this.network = network;
		this.id = id;
        this.protocol = protocol;
	}

    /**
     * Returns the id of the node.
     * @return id of the node.
     */
	public int getId() {
		return this.id;
	}

    /**
     * Adds a new out-link to the node.
     * @param link link to be added as out-link.
     */
    public void addOutLink(Link link) {
        outNeighbours.add(link.getDestination());
    }

    /**
     * Adds a new in-link to the node.
     * @param link link to be added as in-link.
     */
    public void addInLink(Link link) {
        inLinks.add(link);
    }

    /**
     * Returns a collection with all the in-links of the node.
     * @return collection with all the in-links of the node.
     */
    public Collection<Link> getInLinks() {
        return inLinks;
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

    /**
     * Resets the route table and exports the node's self route to all of its in-neighbours.
     * This method must be called before simulating the network and should only be called by the network class.
     */
    void start() {
        try {
            routeTable.clear();
        } catch (NullPointerException e) {
            routeTable = new RouteTable(outNeighbours, network.attrFactory);
        }

        for (Link inLink : inLinks) {
            export(inLink, new Route(this, network.attrFactory.createSelf(this), new PathAttribute(this)));
        }
    }

    /**
     * Executes the learning process of a route. If the new learned route becomes the preferred route it is exported
     * to all of the node's in-neighbours.
     * @param link link from which the route was exported.
     * @param learnedRoute exported route to be learned.
     */
    public void learn(Link link, Route learnedRoute) {
        // store previous attribute and path selections to check if the selected changed
        Attribute previousSelectedAttribute = selectedAttribute;
        Attribute previousSelectedPath = selectedPath;

        Attribute attribute = protocol.extend(link, learnedRoute.getAttribute());

        PathAttribute path;
        if (!attribute.isInvalid()) {
            path = learnedRoute.getPath();
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = new PathAttribute();
        }

        Route exclRoute = routeTable.getSelectedRoute(learnedRoute.getDestination(), link.getDestination());

        if (path.contains(this)) {
            // there is a loop
            if (protocol.isOscillation(link, learnedRoute, attribute, path, exclRoute)) {
                // detected oscillation
                protocol.setParameters(link, learnedRoute, attribute, path, exclRoute);
            }

            attribute = network.attrFactory.createInvalid();
            path = new PathAttribute();
        } else {
            // there is no loop
            if (attribute.compareTo(exclRoute.getAttribute()) < 0) {
                selectedAttribute = attribute;
                selectedPath = path;
            } else {
                selectedAttribute = exclRoute.getAttribute();
                selectedPath = exclRoute.getPath();
            }
        }

        routeTable.setAttribute(learnedRoute.getDestination(), link.getDestination(), attribute);
        routeTable.setPath(learnedRoute.getDestination(), link.getDestination(), path);

        if (previousSelectedAttribute.compareTo(selectedAttribute) != 0 &&
                previousSelectedPath.compareTo(selectedPath) != 0) {

            for (Link inLink : inLinks) {
                // !! it must be exported a new instance (a copy) of Route
                // TODO the exported path must also be a copy
                export(inLink, new Route(this, selectedAttribute, selectedPath));
            }
        }
    }

    /**
     * Exports a route through the given link. The route is put in the network's scheduler.
     * @param link link to export the route to.
     * @param route route to be exported.
     */
    private void export(Link link, Route route) {
        // TODO - implement Node.export
        throw new UnsupportedOperationException();
    }
}