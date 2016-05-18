package simulation.simulators;

import addons.eventhandlers.MessageAndDetectionCountHandler;
import io.ReportGenerator;
import network.Network;
import protocols.D1R1Protocol;
import simulation.State;

import java.io.File;

/**
 * Implements the basic simulator which simulates only the D1R1 protocol and counts the number
 * of messages exchanged and the number of detections.
 */
public class StandardSimulator extends Simulator {

    public StandardSimulator(File networkFile, int destinationId, int repetitions) {
        super(networkFile, destinationId, repetitions);
    }

    /**
     * Initializes the simulation according to the specific simulation configuration.
     * Called before the simulationLoop() method.
     *
     * @param network parsed network.
     */
    @Override
    protected void initSimulation(Network network) {
        state = State.create(network, destinationId, new D1R1Protocol());
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
            MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
            eventHandler.register(engine.getEventGenerator());

            engine.simulate(state);

            reportGenerator.addMessageCount(eventHandler.getMessageCount());
            reportGenerator.addDetectionCount(eventHandler.getDetectionCount());

            state.reset();
            engine.getEventGenerator().clearAll();
        }
    }

}
