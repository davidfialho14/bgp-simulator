package simulators;

import addons.eventhandlers.DebugEventHandler;
import io.reporters.Reporter;
import network.Network;
import simulation.Engine;
import simulation.State;
import simulation.schedulers.RandomScheduler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * This is the base class for all simulators.
 * A simulator simulates a network according to a predefined configuration. This gives the possibility to have
 * different types of simulations. Each simulator implementation simulates specific things and for this reason it
 * holds a specific stats collector.
 */
public abstract class Simulator {

    protected Engine engine;    // engine used for simulation
    protected State state;      // state to be simulated
    private boolean debugEnabled = false;
    private DebugEventHandler debugEventHandler = null;
    private PrintStream debugStream;

    /**
     * Constructs a simulator by creating an initial state to be simulated. For this it calls the protected
     * method createInitialState().
     *
     * @param network network to simulate.
     * @param destinationId id of the destination node.
     * @param minDelay minimum message delay.
     * @param maxDelay maximum message delay.
     */
    public Simulator(Network network, int destinationId, int minDelay, int maxDelay) {
        this.engine = new Engine(new RandomScheduler(minDelay, maxDelay));
        this.state = createInitialState(network, destinationId);
    }

    /**
     * Creates the initial state. Each subclass must implement this method according to its configurations.
     *
     * @param network network to simulate.
     * @param destinationId id of the destination node.
     * @return created state.
     */
    protected abstract State createInitialState(Network network, int destinationId);

    /**
     * Enables the debug report for the simulator. Enabling/disabling the debug report will only take effect in the
     * next call to simulate.
     *
     * @param enable true to debugEnabled and false to disable.
     * @param debugFile file to output debug messages.
     */
    public void enableDebugReport(boolean enable, File debugFile) throws FileNotFoundException {

        if (!this.debugEnabled && enable) { // enabling
            debugEventHandler = new DebugEventHandler(new PrintStream(debugFile), true);
            debugEventHandler.register(engine.getEventGenerator());
        } else if (this.debugEnabled && !enable) { // disabling
            debugEventHandler.unregister(engine.getEventGenerator());
            debugEventHandler = null;
        }

        this.debugEnabled = enable;
    }

    /**
     * Executes one simulation. Should be overridden by subclasses in order to add additional operations
     * prior or after simulation. By default activates the debug report if enabled and calls the simulate() method
     * of the engine.
     */
    public void simulate() {
        state.reset();
        engine.simulate(state);
    }

    /**
     * Calls the reporter generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    public abstract void report(Reporter reporter) throws IOException;

}
