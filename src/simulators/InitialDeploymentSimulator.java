package simulators;

import io.reporters.Reporter;
import network.Network;
import policies.Path;
import protocols.D1R1Protocol;
import simulation.State;
import simulation.events.DetectEvent;
import simulation.events.DetectListener;
import simulation.events.ExportEvent;
import simulation.events.ExportListener;
import simulators.statscollectors.BasicStatsCollector;

import java.io.IOException;

/**
 * The initial deployment simulator simulates a network with the detection already activated from the start
 * for all nodes.
 */
public class InitialDeploymentSimulator extends Simulator implements ExportListener, DetectListener {

    protected BasicStatsCollector statsCollector;

    /**
     * Constructs a simulator by creating an initial state to be simulated. For this it calls the protected
     * method createInitialState(). Initializes the stats collector.
     *
     * @param network       network to simulate.
     * @param destinationId id of the destination node.
     * @param minDelay      minimum message delay.
     * @param maxDelay      maximum message delay.
     */
    public InitialDeploymentSimulator(Network network, int destinationId, int minDelay, int maxDelay) {
        super(network, destinationId, minDelay, maxDelay);
        this.statsCollector = createStatsCollector(network.getNodeCount(), network.getLinkCount());

        // register to listen for export events
        this.engine.getEventGenerator().addExportListener(this);
        this.engine.getEventGenerator().addDetectListener(this);
    }

    /**
     * Creates the initial state. Each subclass must implement this method according to its configurations.
     *
     * @param network       network to simulate.
     * @param destinationId id of the destination node.
     * @return created state.
     */
    @Override
    protected State createInitialState(Network network, int destinationId) {
        return State.create(network, destinationId, new D1R1Protocol());
    }

    /**
     * Called in the constructor to create the stats collector. By default it returns a BasicStatsCollector object.
     * Each subclass should override this method if this is not the intended behaviour.
     *
     * @param nodeCount number of nodes in the network.
     * @param linkCount number of links in the network.
     * @return new basic stats collector object.
     */
    protected BasicStatsCollector createStatsCollector(int nodeCount, int linkCount) {
        return new BasicStatsCollector(nodeCount, linkCount);
    }

    /**
     * Executes one simulation. Stats are stored for each simulation, no stat is ever discarded.
     */
    @Override
    public void simulate() {
        statsCollector.newSimulation();
        super.simulate();
    }

    /**
     * Calls the reporter generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        reporter.generate(statsCollector);
    }

    /**
     * Invoked when a new message is sent.
     */
    @Override
    public void onExported(ExportEvent event) {
        statsCollector.newMessage();
    }

    /**
     * Invoked when a detect event occurs.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = event.getLearnedRoute().getPath().getSubPathBefore(event.getDetectingNode());
        cycle.add(event.getDetectingNode());    // include the detecting node in the start and end of the cycle path

        statsCollector.newDetection(event.getDetectingNode(), event.getOutLink(), cycle);
    }
}
