package network;


import java.util.*;

public class Node {

    private Network network;    // network who created the node

    private int id;   // id must be unique in each network
    private List<Node> outNeighbours = new ArrayList<>();
    private List<Link> inLinks = new ArrayList<>();

    // fields used during simulation
    RouteTable routeTable;
    Protocol protocol;
    Map<Node, Attribute> selectedAttributes;    // currently selected attribute
    Map<Node, PathAttribute> selectedPaths;     // currently selected path

    /**
     * @param network   network who created the node.
	 * @param id    id to assign to the node.
	 */
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

    /**
     * Returns a collection with all the out-neighbours of the node.
     * @return collection with all the out-neighbours of the node.
     */
    public Collection<Node> getOutNeighbours() {
        return outNeighbours;
    }

    /**
     * Returns the node's current route table.
     * @return route table.
     */
    public RouteTable getRouteTable() {
        return routeTable;
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
     * Resets the route table and the selected routes. This method must be called before simulating the network and 
     * should only be called by the network class.
     */
    void startTable() {
        try {
            routeTable.clear();
        } catch (NullPointerException e) {
            routeTable = new RouteTable(outNeighbours, network.getAttrFactory());
        }

        try {
            selectedAttributes.clear();
        } catch (NullPointerException e) {
            selectedAttributes = new HashMap<>();
        }

        try {
            selectedPaths.clear();
        } catch (NullPointerException e) {
            selectedPaths = new HashMap<>();
        }
    }

    /**
     * Exports the node's self route to all of its in-neighbours. This method must be called before simulating the 
     * network and should only be called by the network class.
     */
    void exportSelf() {
        for (Link inLink : inLinks) {
            network.export(inLink,
                    new Route(this, network.getAttrFactory().createSelf(this), new PathAttribute(this)));
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
        Attribute previousSelectedAttribute = selectedAttributes.get(learnedRoute.getDestination());
        PathAttribute previousSelectedPath = selectedPaths.get(learnedRoute.getDestination());

        Attribute attribute = protocol.extend(link, learnedRoute.getAttribute());

        PathAttribute path;
        if (!attribute.isInvalid()) {
            path = learnedRoute.getPath();
            path.add(link.getDestination());    // add exporter to the path
        } else {
            path = PathAttribute.createInvalid();
        }

        Route exclRoute = routeTable.getSelectedRoute(learnedRoute.getDestination(), link.getDestination());

        Attribute selectedAttribute = previousSelectedAttribute;
        PathAttribute selectedPath = previousSelectedPath;

        if (path.contains(this)) {
            // there is a loop
            if (protocol.isOscillation(link, learnedRoute, attribute, path, exclRoute)) {
                // detected oscillation
                protocol.setParameters(link, learnedRoute, attribute, path, exclRoute);
            }

            attribute = network.getAttrFactory().createInvalid();
            path = PathAttribute.createInvalid();
        } else {
            // there is no loop
            if (exclRoute == null || attribute.compareTo(exclRoute.getAttribute()) < 0) {
                selectedAttribute = attribute;
                selectedPath = path;
            } else {
                selectedAttribute = exclRoute.getAttribute();
                selectedPath = exclRoute.getPath();
            }
        }

        selectedAttributes.put(learnedRoute.getDestination(), selectedAttribute);
        selectedPaths.put(learnedRoute.getDestination(), selectedPath);
        routeTable.setAttribute(learnedRoute.getDestination(), link.getDestination(), attribute);
        routeTable.setPath(learnedRoute.getDestination(), link.getDestination(), path);

        if (previousSelectedAttribute == null ||
                    !previousSelectedAttribute.equals(selectedAttribute) ||
                            !previousSelectedPath.equals(selectedPath)) {

            for (Link inLink : inLinks) {
                // !! it must be exported a new instance (a copy) of Route
                network.export(inLink,
                        new Route(learnedRoute.getDestination(), selectedAttribute, new PathAttribute(selectedPath)));
            }
        }
    }

    @Override
    public String toString() {
        return "Node(" + id + ')';
    }

}