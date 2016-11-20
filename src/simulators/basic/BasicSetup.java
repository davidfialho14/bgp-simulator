package simulators.basic;

import core.Destination;
import core.Engine;
import core.Topology;
import io.reporters.Reporter;
import main.Parameters;
import simulators.DataCollector;
import simulators.Setup;

import java.io.IOException;

/**
 * In a basic setup the topology is not changed. Is equivalent to the advertisement of a new prefix. It
 * uses a basic data collector to collect the data.
 */
public class BasicSetup extends Setup {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final BasicDataCollector dataCollector = new BasicDataCollector();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new basic setup.
     *
     * @param engine        engine used for the simulation.
     * @param topology      topology to simulate.
     * @param destination   destination to simulate for.
     */
    public BasicSetup(Engine engine, Topology topology, Destination destination) {
        super(engine, topology, destination);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a basic data collector.
     *
     * @return basic data collector.
     */
    @Override
    public DataCollector getDataCollector() {
        return dataCollector;
    }

    /**
     * Reports the setup using the given reporter implementation.
     *
     * @param reporter      reporter used to report (Visitor)
     * @param parameters    parameters of the simulator.
     */
    @Override
    public void report(Reporter reporter, Parameters parameters) throws IOException {
        reporter.reportSetup(this, parameters);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Implementation of Template Methods (for the Simulator class)
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Clears the data from the previous simulation.
     */
    @Override
    public void setup() {
        dataCollector.clear();
    }

    /**
     * Does nothing.
     */
    @Override
    public void cleanup() {
    }

}
