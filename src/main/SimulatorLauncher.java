package main;

import core.Engine;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.networkreaders.TopologyReader;
import io.networkreaders.exceptions.TopologyParseException;
import io.reporters.Reporter;
import simulators.Simulator;

import java.io.IOException;


/**
 * The simulator launcher class is responsible for the execution of simulations. The launcher a pre-configured
 * and then its launch() method executes the simulation: loads the topology file, runs all simulations instances,
 * and reports its results. In case of errors it calls the proper methods of the error handler.
 */
public class SimulatorLauncher {

    private final ErrorHandler errorHandler;
    private SimulatorParameters parameters = null;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new simulator launcher associated with an error handler.
     *
     * @param errorHandler class to handle errors.
     */
    public SimulatorLauncher(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void setParameters(SimulatorParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Starting point of any simulation. This method should be called after configuring the simulation. After calling
     * this method the simulation will load the topology from the topology file, simulate according to the predefined
     * configurations, and dump a proper report.
     */
    public void launch() {

        if (parameters == null) {
            throw new IllegalStateException("can not launch the simulator before the parameters have been set");
        }

        // Load the topology
        Topology topology = null;
        try (TopologyReader topologyReader =
                     parameters.getReaderFactory().getTopologyReader(parameters.getTopologyFile())) {

            topology = topologyReader.read();

        } catch (IOException e) {
            errorHandler.onTopologyLoadIOException(e);

        } catch (TopologyParseException e) {
            errorHandler.onTopologyLoadParseException(e);
        }

        if (topology != null) { // if the topology was loaded successfully

            // load the engine
            Engine engine = new Engine(new RandomScheduler(parameters.getMinDelay(), parameters.getMaxDelay()));

            // use the simulator factory to get a properly configured simulator
            Simulator simulator = parameters.getSimulatorFactory().getSimulator(
                    engine, topology, parameters.getDestinationId());

            try  {
                Reporter reporter = parameters.getReporterFactory().getReporter(parameters.getReportFile());
                reporter.dumpBasicInfo(topology, parameters.getDestinationId(), parameters.getMinDelay(),
                        parameters.getMaxDelay(), parameters.getProtocol(), simulator);

                for (int i = 0; i < parameters.getRepetitionCount(); i++) {
                    simulator.simulate();
                    simulator.report(reporter);
                }

            } catch (IOException e) {
                errorHandler.onReportingIOException(e);
            }

        }

    }

}
