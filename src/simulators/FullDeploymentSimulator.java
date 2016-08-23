package simulators;

import addons.protocolchangers.FixedTimeProtocolChanger;
import core.Engine;
import core.Protocol;
import core.State;
import io.reporters.Reporter;
import simulators.data.FullDeploymentDataCollector;

import java.io.IOException;

/**
 * The initial deployment simulator simulates a topology with the detection already activated for all nodes from
 * the start to the end of the simulation.
 */
public class FullDeploymentSimulator extends Simulator {

    protected long deployTime;
    protected FullDeploymentDataCollector fullDeploymentDataCollector;
    private Protocol deployProtocol;

    FullDeploymentSimulator(Engine engine, State initialState, Protocol deployProtocol, long deployTime,
                            FullDeploymentDataCollector fullDeploymentDataCollector) {
        super(engine, initialState);
        this.deployProtocol = deployProtocol;
        this.deployTime = deployTime;
        this.fullDeploymentDataCollector = fullDeploymentDataCollector;

        this.fullDeploymentDataCollector.register(engine);
    }

    /**
     * Executes one simulation. Should be overridden by subclasses in order to add additional operations
     * prior or after simulation. By default activates the debug report if enabled and calls the simulate() method
     * of the engine.
     */
    @Override
    public void simulate() {
        // setup protocol changer
        new FixedTimeProtocolChanger(engine, state, deployTime) {

            @Override
            public void onTimeToChange() {
                deployProtocol.reset();
                changeAllProtocols(deployProtocol);
                fullDeploymentDataCollector.setDeployed(true);
            }
        };

        fullDeploymentDataCollector.clear();
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
        fullDeploymentDataCollector.dump(reporter);
    }

    /**
     * Returns a string identifying the type of simulator.
     *
     * @return string "Full".
     */
    @Override
    public String toString() {
        return "Full";
    }

}
