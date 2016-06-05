package simulators;

import io.reporters.Reporter;
import network.Network;
import protocols.Protocol;
import simulation.Engine;
import simulators.data.BasicDataCollector;
import simulators.data.BasicDataSet;

import java.io.IOException;

/**
 * The initial deployment simulator simulates a network with the detection already activated for all nodes from
 * the start to the end of the simulation.
 */
public class InitialDeploymentSimulator extends Simulator {

    private BasicDataCollector dataCollector;

    public InitialDeploymentSimulator(Engine engine, Network network, int destinationId, Protocol deployProtocol) {
        super(engine, network, destinationId, deployProtocol);
        this.dataCollector = new BasicDataCollector(engine, new BasicDataSet());
    }

    /**
     * Executes one simulation. Should be overridden by subclasses in order to add additional operations
     * prior or after simulation. By default activates the debug report if enabled and calls the simulate() method
     * of the engine.
     */
    @Override
    public void simulate() {
        dataCollector.getDataset().clear(); // clear the dataset for each simulation
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
        reporter.dump(dataCollector.getDataset());
    }

}
