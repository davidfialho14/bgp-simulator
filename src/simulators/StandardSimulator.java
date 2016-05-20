package simulators;

import io.reporters.Reporter;
import network.Network;
import protocols.D1R1Protocol;
import simulation.State;
import simulation.events.DetectEvent;
import simulation.events.DetectListener;
import simulation.events.ExportEvent;
import simulation.events.ExportListener;
import simulators.statscollectors.BasicStatsCollector;

import java.io.IOException;

/**
 * The standard simulator simulate a network with all nodes detecting from start to end.
 */
public class StandardSimulator extends Simulator implements ExportListener, DetectListener {

    private BasicStatsCollector statsCollector;

    /**
     * Constructs a simulator by creating an initial state to be simulated. For this it calls the protected
     * method createInitialState(). Initializes the stats collector.
     *
     * @param network       network to simulate.
     * @param destinationId id of the destination node.
     */
    public StandardSimulator(Network network, int destinationId) {
        super(network, destinationId);
        this.statsCollector = new BasicStatsCollector(network.getNodeCount(), network.getLinkCount());

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
     * Executes one simulation. Stats are stored for each simulation, no stat is ever discarded.
     */
    @Override
    public void simulate() {
        statsCollector.newSimulation();
        state.reset();

        engine.simulate(state);
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
        statsCollector.newDetection(event.getDetectingNode(), event.getOutLink());
    }
}
