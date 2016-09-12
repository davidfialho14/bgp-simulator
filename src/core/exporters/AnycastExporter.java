package core.exporters;

import core.Route;
import core.State;
import core.topology.ConnectedNode;


/**
 * The anycast exporter supports the destination export using anycast, with multiple nodes exporting a route to the
 * same destination.
 */
public class AnycastExporter extends AbstractExporter {

    private final AnycastMap anycastMap;

    /**
     * Creates a new anycast exporter initialized with the given anycast map.
     *
     * @param anycastMap anycast map used to map destinations to nodes.
     */
    public AnycastExporter(AnycastMap anycastMap) {
        this.anycastMap = anycastMap;
    }

    /**
     * Exports the initial route(s) from the destination. The simulation engine calls this method to start the
     * simulation process. It adds the exported self-route to the destination node's route table.
     *
     * @param initialState  initial state used to get the destination node and respective state
     */
    @Override
    public void exportDestination(State initialState) {
        // take the fake destination node
        ConnectedNode fakeDestination = anycastMap.getDestination(initialState.getDestinationId());

        if (fakeDestination != null) {
            // export a self route to all in-neighbours
            // it is not necessary to update any route table since destination is fake
            Route selfRoute = Route.createSelf(fakeDestination, initialState.getTopology().getPolicy());
            fakeDestination.getInLinks()
                    .forEach(link -> this.export(link, selfRoute));

        } else {
            // the destination is real and maybe unicasted as normal
            UnicastExporter.unicastExportDestination(this, initialState);
        }

    }

}
