package simulation;

import network.Link;
import network.Network;
import network.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * Engine implements the hardcore simulation logic.
 */
public class SimulateEngine {

    private Protocol protocol;
    private AttributeFactory attributeFactory;
    private Scheduler scheduler;
    private EventHandler eventHandler;

    // state information of the nodes during and after simulation
    private Map<Node, NodeStateInfo> nodesStateInfo = new HashMap<>();

    /**
     * Initializes a new SimulateEngine.
     * @param protocol routing protocol to be used.
     * @param attributeFactory factory used to create attributes.
     * @param scheduler scheduler used to schedule exported routes.
     * @param eventHandler event handler called on any new event.
     */
    public SimulateEngine(Protocol protocol, AttributeFactory attributeFactory, Scheduler scheduler, EventHandler eventHandler) {
        this.protocol = protocol;
        this.attributeFactory = attributeFactory;
        this.scheduler = scheduler;
        this.eventHandler = eventHandler;
    }

    /**
     * Simulates the BGP protocol according to the specifications of the engine for the given network.
     * During simulation the slot methods of the event handler are called in the appropriate time.
     * @param network network to be simulated.
     */
    public void simulate(Network network) {
        initNodesStateInfo(network);
        exportNodesSelfRoutes(network);

        // simulation loop
        ScheduledRoute scheduledRoute;
        while ((scheduledRoute = scheduler.get()) != null) {
            Node learningNode = scheduledRoute.getLink().getSource();
            processScheduledRoute(scheduledRoute, nodesStateInfo.get(learningNode));
        }
    }

    //------------- PACKAGE METHODS -----------------------------------------------------------------------------------

    void processScheduledRoute(ScheduledRoute scheduledRoute, NodeStateInfo nodeStateInfo) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    Route learn(Link link, Route route) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    Route select(Link link, Route learnedRoute, NodeStateInfo stateInfo) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    void export(Link inLink, Route route) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    //------------- PRIVATE METHODS -----------------------------------------------------------------------------------

    /**
     * Initializes the network's nodes state info with the default state information for each node.
     * @param network network being simulated.
     */
    private void initNodesStateInfo(Network network) {
        nodesStateInfo.clear();
        for (Node node : network.getNodes()) {
            nodesStateInfo.put(node, new NodeStateInfo(node, attributeFactory));
        }
    }

    /**
     * Exports all self routes from all nodes in the given network.
     * @param network network being simulated.
     */
    private void exportNodesSelfRoutes(Network network) {
        for (Node node : network.getNodes()) {
            for (Link inLink : node.getInLinks()) {
                export(inLink, Route.createSelf(node, attributeFactory));
            }
        }
    }
}
