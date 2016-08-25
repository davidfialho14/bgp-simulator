package simulators.basic;

import core.Engine;
import core.State;
import simulators.Dataset;
import simulators.Simulator;

/**
 * The basic simulator executes a simulation without ever modifying the initial state. It uses a basic data collector.
 */
public class BasicSimulator extends Simulator {

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
     * Creates a new simulator given an engine and initial state.
     *
     * @param engine       engine used for the simulation.
     * @param initialState initial state.
     */
    public BasicSimulator(Engine engine, State initialState) {
        super(engine, initialState);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Gives access to the data collector used by the simulator.
     *
     * @return instance of the data collector used by the simulator.
     */
    @Override
    public Dataset getData() {
        return dataCollector.getDataset();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Implementation of Template Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * (Template Method)
     * <p>
     * Clears the data collector data and registers the data collector.
     */
    @Override
    protected void setup() {
        dataCollector.clear();
        dataCollector.register(engine);
    }

    /**
     * (Template Method)
     * <p>
     * Unregisters the data collector. Can not clear data here, otherwise the data could not ever be reported.
     */
    @Override
    protected void cleanup() {
        dataCollector.unregister();
    }

    /**
     * Returns an identification of the simulator.
     *
     * @return string "Basic"
     */
    @Override
    public String toString() {
        return "Basic";
    }

}
