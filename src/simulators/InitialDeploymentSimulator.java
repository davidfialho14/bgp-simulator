package simulators;

import core.Engine;
import core.State;
import io.reporters.Reporter;
import simulators.data.BasicDataCollector;

import java.io.IOException;

/**
 * The initial deployment simulator simulates a topology with the detection already activated for all nodes from
 * the start to the end of the simulation.
 */
public class InitialDeploymentSimulator extends Simulator {

    protected BasicDataCollector basicDataCollector;

    InitialDeploymentSimulator(Engine engine, State initialState, BasicDataCollector basicDataCollector) {
        super(engine, initialState);
        this.basicDataCollector = basicDataCollector;

        basicDataCollector.register(engine);
    }

    /**
     * Executes one simulation. Should be overridden by subclasses in order to add additional operations
     * prior or after simulation. By default activates the debug report if enabled and calls the simulate() method
     * of the engine.
     */
    @Override
    public void simulate() {
        basicDataCollector.clear();
        super.simulate();
    }

    /**
     * Calls the reporter's generate() method to generate a report with the collected stats.
     *
     * @param reporter reporter to generate report.
     * @throws IOException if an error with the output file occurs.
     */
    @Override
    public void report(Reporter reporter) throws IOException {
        basicDataCollector.dump(reporter);
    }

}
