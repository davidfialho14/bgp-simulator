package simulators;

import io.reporters.Reporter;
import network.Network;
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
     * Constructs a simulator by creating an initial state to be simulated. For this it calls the protected
     * method createInitialState().
     *
     * @param network network to simulate.
     * @param destinationId id of the destination node.
     */
    public Simulator(Network network, int destinationId) {
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
