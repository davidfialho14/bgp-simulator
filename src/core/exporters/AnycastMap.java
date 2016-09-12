package core.exporters;

import core.topology.ConnectedNode;
import core.topology.Label;
import core.topology.Link;

import java.util.HashMap;
import java.util.Map;


/**
 * An anycast map associates a destination ID with one or many anycast nodes.
 */
public class AnycastMap {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private implementation using a map
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // maps the destination integer ID to its connected node representation
    private final Map<Integer, ConnectedNode> destinations = new HashMap<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Puts a new anycast association in the map, associating the destination ID with the given export node. The
     * attribute and path length are used to specify which route will the export node advertise.
     *
     * @param destinationId ID of the destination node to anycast.
     * @param neighbour     node actually exporting a route to the destination.
     * @param label         label of the link between the neighbour and the destination node.
     */
    public void put(int destinationId, ConnectedNode neighbour, Label label) {
        // try to get the anycast nodes list
        ConnectedNode destination = this.destinations.get(destinationId);

        if (destination == null) {
            // the destination does not exist yet
            // add destination with the new in-link
            destination = new ConnectedNode(destinationId);
            this.destinations.put(destinationId, destination);
        }

        // add new in-link to the destination
        destination.addInLink(new Link(neighbour, destination, label));
    }

    /**
     * Returns a fake connected node representing the destination and containing the in-links to real connected nodes
     * of the topology.
     *
     * @param destinationId ID of the destination node.
     * @return fake connected node representing the destination and containing the in-links to real connected nodes
     * of the topology.
     */
    public ConnectedNode getDestination(int destinationId) {
        return destinations.get(destinationId);
    }

}
