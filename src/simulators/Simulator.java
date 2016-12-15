package simulators;

import core.Link;
import core.Router;
import io.reporters.Reporter;

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

        // reset the routers
        for (Router router : setup.getTopology().getRouters()) {

            router.getTable().reset();
            router.getMRAITimer().clear();

            // reset router's links
            for (Link link : router.getInLinks()) {
                link.setTurnedOff(false);
                link.setLastArrivalTime(0);
            }

        }

    }

    /**
     * Reports the results of the last simulation.
     *
     * @param reporter          reporter implementation to report data.
     * @param simulationNumber  number of the simulation being reported.
     * @throws IOException if there is an IO error while reporting.
     */
    public void report(Reporter reporter, int simulationNumber) throws IOException {
        setup.getDataCollector().report(reporter, simulationNumber);
    }

}
