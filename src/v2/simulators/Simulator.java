package v2.simulators;

import v2.io.reporters.Reporter;

import java.io.IOException;

/**
 * A simulator is used to do the actual simulation. It is a template class that takes a simulation setup
 * and executes teh necessary steps to start a simulation: setup, start, and cleanup.
 */
public class Simulator {

    private Setup setup;    // setup of the simulation

    /**
     * Creates a simulator and assigns it an initial setup.
     *
     * @param setup setup to assign to the new simulator.
     */
    public Simulator(Setup setup) {
        this.setup = setup;
    }

    /**
     * Returns the simulation setup.
     *
     * @return the simulation setup.
     */
    public Setup getSetup() {
        return setup;
    }

    /**
     * Sets a simulation setup for the simulator.
     *
     * @param setup setup to set.
     */
    public void setSetup(Setup setup) {
        this.setup = setup;
    }

    /**
     * Entry point to start the simulations.
     */
    public void simulate() {
        setup.setup();
        setup.start();
        setup.cleanup();
    }

    /**
     * Reports the results of the last simulation.
     *
     * @param reporter  reporter implementation to report data.
     * @throws IOException if there is an IO error while reporting.
     */
    public void report(Reporter reporter) throws IOException {
        setup.getDataCollector().report(reporter);
    }

}
