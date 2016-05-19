package simulators;

import io.reporters.Reporter;
import simulation.Engine;
import simulation.State;
import simulation.schedulers.RandomScheduler;

import java.io.IOException;

/**
 * This is the base class for all simulators.
 * A simulator simulates a network according to a predefined configuration. This gives the possibility to have
 * different types of simulations. Each simulator implementation simulates specific things and for this reason it
 * holds a specific stats collector.
 */
public abstract class Simulator {

    protected Engine engine = new Engine(new RandomScheduler());    // engine used for simulation
    protected State state;                                          // state to be simulated

    /**
     * Constructs a simulator by assigning it the state to be simulated.
     *
     * @param state state to be simulated.
     */
    public Simulator(State state) {
        this.state = state;
    }

    /**
     * Executes one simulation. Stats are stored for each simulation, no stat is ever discarded.
     */
    public abstract void simulate();

    /**
     * Calls the reporter generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    public abstract void report(Reporter reporter) throws IOException;

}
