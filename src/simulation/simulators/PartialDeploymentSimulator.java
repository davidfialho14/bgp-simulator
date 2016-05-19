package simulation.simulators;

import io.Reporter;
import network.Network;
import protocols.BGPProtocol;
import simulation.State;

import java.io.File;

/**
 * Implements the partial deployment simulator which starts all nodes using the BGP protocol and after
 * a given moment of time all nodes start to detect. It counts the number of messages
 */
public class PartialDeploymentSimulator extends Simulator {

    private long timeToChange;

    public PartialDeploymentSimulator(File networkFile, int destinationId, int repetitions, boolean debug,
                                      long timeToChange) {
        super(networkFile, destinationId, repetitions, debug);
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

        // FIXME replace with a stats collector
        // StatsManager statsManager = new StatsManager(engine, state, timeToChange);

        engine.simulate(state);

        // FIXME no longer needed when using a stats collector
        // reporter.addCount(MESSAGE_COUNT_STAT, statsManager.getMessageCount());
        // reporter.addCount(CUT_OFF_LINKS_COUNT_STAT, statsManager.getCutOffLinkCount());
        // reporter.addCount(DETECTING_NODES_COUNT_STAT, statsManager.getDetectingNodesCount());
    }

}
