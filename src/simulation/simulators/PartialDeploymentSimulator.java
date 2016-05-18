package simulation.simulators;

import addons.statsmanagers.StatsManager;
import io.Reporter;
import io.stats.CutOffLinksCountStat;
import io.stats.DetectingNodesCountStat;
import io.stats.MessageCountStat;
import network.Network;
import protocols.BGPProtocol;
import simulation.State;

import java.io.File;

/**
 * Implements the partial deployment simulator which starts all nodes using the BGP protocol and after
 * a given moment of time all nodes start to detect. It counts the number of messages
 */
public class PartialDeploymentSimulator extends Simulator {

    private static final MessageCountStat MESSAGE_COUNT_STAT = new MessageCountStat();
    private static final CutOffLinksCountStat CUT_OFF_LINKS_COUNT_STAT = new CutOffLinksCountStat();
    private static final DetectingNodesCountStat DETECTING_NODES_COUNT_STAT = new DetectingNodesCountStat();

    private long timeToChange;

    public PartialDeploymentSimulator(File networkFile, int destinationId, int repetitions, long timeToChange) {
        super(networkFile, destinationId, repetitions);
        this.timeToChange = timeToChange;
    }

    /**
     * Initializes the simulation according to the specific simulation configuration.
     * Called before the simulationLoop() method.
     *
     * @param network parsed network.
     */
    @Override
    protected void initSimulation(Network network) {
        state = State.create(network, destinationId, new BGPProtocol());
    }

    /**
     * Invoked to execute each repetition of the simulation according to the specific simulation configuration.
     * When this method is invoked the initSimulation() was already called.
     *
     * @param reporter generator to add simulation data to.
     */
    @Override
    protected void executeSimulation(Reporter reporter) {

        StatsManager statsManager = new StatsManager(engine, state, timeToChange);

        engine.simulate(state);

        reporter.addCount(MESSAGE_COUNT_STAT, statsManager.getMessageCount());
        reporter.addCount(CUT_OFF_LINKS_COUNT_STAT, statsManager.getCutOffLinkCount());
        reporter.addCount(DETECTING_NODES_COUNT_STAT, statsManager.getDetectingNodesCount());
    }

}
