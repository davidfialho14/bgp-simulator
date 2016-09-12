package core.exporters;

import core.NodeState;
import core.Route;
import core.State;
import core.topology.ConnectedNode;
import core.topology.SelfLink;

/**
 * The unicast exporter is the most basic exporter implementation. It implements the exportDestination() method by
 * having the destination node export its self-route to all of its in-neighbours.
 */
public class UnicastExporter extends AbstractExporter {

    /**
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process. It adds the exported self-route to the destination node's route table.
     *
     * @param initialState  initial state used to get the destination node and respective state
     */
    @Override
    public void exportDestination(State initialState) {
        unicastExportDestination(this, initialState);
    }

    /**
     * This method was created only to be used by another exporter when wanting to perform the unicast export.
     */
    static void unicastExportDestination(Exporter exporter, State initialState) {
        ConnectedNode destinationNode = initialState.getDestination();
        NodeState nodeState = initialState.get(destinationNode);

        // create a self route for the destination node
        Route selfRoute = Route.createSelf(destinationNode, initialState.getTopology().getPolicy());

        // add the self route to the node's route table
        nodeState.getTable().setRoute(new SelfLink(destinationNode), selfRoute);

        destinationNode.getInLinks().forEach(link -> exporter.export(link, selfRoute));
    }

}
