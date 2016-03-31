package simulation;

import network.Link;
import network.Network;
import network.Node;

import java.util.Map;

/**
 * Engine implements the hardcore simulation logic.
 */
public class SimulateEngine {

    Protocol protocol;
    AttributeFactory attributeFactory;
    Scheduler scheduler;
    EventHandler eventHandler;

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

    public void simulate(Network network) {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    Map<Node, RouteTable> createRouteTables() {
        // TODO implement this method
        throw new UnsupportedOperationException("not yet implemented");
    }

    void processScheduledRoute(ScheduledRoute scheduledRoute) {
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
}
