package simulation.simulators;

import addons.statsmanagers.StatsManager;
import io.ReportGenerator;
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
     * Invoked to execute the simulation loop according to the specific simulation configuration.
     * When this method is invoked the initSimulation() was already called.
     *
     * @param reportGenerator generator to add simulation data to.
     */
    @Override
    protected void simulationLoop(ReportGenerator reportGenerator) {

        for (int i = 0; i < repetitions; i++) {
            StatsManager statsManager = new StatsManager(engine, state, timeToChange);

            engine.simulate(state);

            reportGenerator.addMessageCount(statsManager.getMessageCount());
            reportGenerator.addCutOffLinksCount(statsManager.getCutOffLinkCount());
            reportGenerator.addDetectingNodesCount(statsManager.getDetectingNodesCount());

            state.reset();
            engine.getEventGenerator().clearAll();
        }

    }

}
