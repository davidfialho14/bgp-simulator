package simulators;

import addons.protocolchangers.FixedTimeProtocolChanger;
import io.reporters.Reporter;
import network.Network;
import protocols.BGPProtocol;
import protocols.D1R1Protocol;
import simulation.State;
import simulators.statscollectors.BasicStatsCollector;
import simulators.statscollectors.FullDeploymentStatsCollector;

import java.io.IOException;

public class FullDeploymentSimulator extends StandardSimulator {

    private final long deployTime;

    // wrapper around the stats collector to avoid having to cast every time there is the need to access
    // full deployment stats collector features.
    private FullDeploymentStatsCollector fullDeploymentStatsCollector;

    /**
     * Constructs a simulator by creating an initial state to be simulated. For this it calls the protected
     * method createInitialState(). Initializes the stats collector.
     *
     * @param network       network to simulate.
     * @param destinationId id of the destination node.
     * @param minDelay      minimum message delay.
     * @param maxDelay      maximum message delay.
     * @param deployTime    time to start deployment.
     */
    public FullDeploymentSimulator(Network network, int destinationId, int minDelay, int maxDelay, long deployTime) {
        super(network, destinationId, minDelay, maxDelay);
        this.deployTime = deployTime;

        // create a cast of the basic stats collector
        fullDeploymentStatsCollector = (FullDeploymentStatsCollector) statsCollector;
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
        return State.create(network, destinationId, new BGPProtocol());
    }

    /**
     * Executes one simulation. Stats are stored for each simulation, no stat is ever discarded.
     */
    @Override
    public void simulate() {
        // setup protocol changer
        new FixedTimeProtocolChanger(engine, state, deployTime) {
            @Override
            public void onTimeChange(long newTime) {
                if (isTimeToChange(newTime)) {
                    changeAllProtocols(new D1R1Protocol());
                    fullDeploymentStatsCollector.notifyDeployment();
                }
            }
        };

        super.simulate();
    }

    /**
     * Returns a FullDeploymentStatsCollector.
     *
     * @return new FullDeploymentStatsCollector object.
     */
    @Override
    protected BasicStatsCollector createStatsCollector(int nodeCount, int linkCount) {
        return new FullDeploymentStatsCollector(nodeCount, linkCount);
    }

    /**
     * Calls the reporter generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        reporter.generate(fullDeploymentStatsCollector);
    }
}
