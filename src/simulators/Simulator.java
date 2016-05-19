package simulators;

import addons.eventhandlers.DebugEventHandler;
import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.TokenMgrError;
import io.InvalidTagException;
import io.NetworkParser;
import io.Reporter;
import network.Network;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import simulation.Engine;
import simulation.State;
import simulation.schedulers.RandomScheduler;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Base class to model a simulator. This models any simulator implementing the basic operations for a simulation.
 * Simulators are configured using the user's configuration input for the simulation.
 */
public abstract class Simulator {

    protected File networkFile;
    protected int destinationId;
    protected int repetitions;
    protected boolean debug;

    protected Engine engine = new Engine(new RandomScheduler());
    protected State state;

    public Simulator(File networkFile, int destinationId, int repetitions, boolean debug) {
        this.networkFile = networkFile;
        this.destinationId = destinationId;
        this.repetitions = repetitions;
        this.debug = debug;
    }

    /**
     * Executes the simulation based on the simulator configurations.
     *
     * @param reporter generator to output report of the simulation.
     * @throws IOException if an error occurs while trying to open the network file.
     * @throws ParseException if the network file is corrupted.
     */
    public void simulate(Reporter reporter) throws IOException, ParseException {
        try {
            NetworkParser parser = new NetworkParser();
            parser.parse(networkFile);

            if (debug) {
                DebugEventHandler debugEventHandler = new DebugEventHandler(
                               new PrintStream(networkFile.getPath().replaceFirst("\\.gv", ".debug")), true);
                debugEventHandler.register(engine.getEventGenerator());
            }

            initSimulation(parser.getNetwork());
            for (int i = 0; i < repetitions; i++) {
                executeSimulation(reporter);
                clearSimulation();
            }

        } catch (TokenMgrError | ParseException | NodeExistsException | NodeNotFoundException | InvalidTagException e) {
            throw new ParseException("network file is corrupted");
        }
    }

    /**
     * Initializes the simulation according to the specific simulation configuration.
     * Called before the simulationLoop() method.
     *
     * @param network parsed network.
     */
    protected abstract void initSimulation(Network network);

    /**
     * Invoked to execute each repetition of the simulation according to the specific simulation configuration.
     * When this method is invoked the initSimulation() was already called.
     *
     * @param reporter generator to add simulation data to.
     */
    protected abstract void executeSimulation(Reporter reporter);

    /**
     * Invoked after each repetition to clear the state for the next simulation.
     * By default it resets the state and clears the event generator of the engine.
     * Subclasses should override this method to implement a proper clearing depending of its needs.
     */
    protected void clearSimulation() {
        state.reset();
        engine.getEventGenerator().clearAll();
    }

}
