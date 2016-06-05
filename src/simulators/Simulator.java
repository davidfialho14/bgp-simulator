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
 * Simulator is the base class that all simulators must extend.
 * This class should be subclasses to implement the different types of simulations.
 * A simulator is responsible for executing simulations for a network and be able to report the data collected
 * during that simulation.
 */
public abstract class Simulator {

    protected Engine engine;    // engine used for simulation
    protected State state;      // state to be simulated

    private boolean debugEnabled = false;
    private DebugEventHandler debugEventHandler = null;

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

    // --- BEGIN PUBLIC INTERFACE ---

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
     * Calls the reporter's generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    public abstract void report(Reporter reporter) throws IOException;

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

    // --- END PUBLIC INTERFACE ---

    /**
     * Creates the initial state to start the simulation with.
     *
     * @param network       network to be simulated.
     * @param destinationId id for the destination to simulate for.
     */
    protected abstract State createInitialState(Network network, int destinationId);



}
