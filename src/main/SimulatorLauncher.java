package main;

import addons.eventhandlers.DebugEventHandler;
import core.Engine;
import core.exporters.AnycastExporter;
import core.exporters.AnycastMap;
import core.exporters.Exporter;
import core.exporters.UnicastExporter;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.AnycastFileReader;
import io.ParseException;
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
    private final ProgressHandler progressHandler;
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
     * @param progressHandler class to handle progress events.
     */
    public SimulatorLauncher(ErrorHandler errorHandler, ProgressHandler progressHandler) {
        this.errorHandler = errorHandler;
        this.progressHandler = progressHandler;
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
     * configurations, and write a proper report.
     */
    public void launch() {

        if (parameters == null) {
            throw new IllegalStateException("can not launch the simulator before the parameters have been set");
        }

        progressHandler.onStartExecution();

        // loading the topology
        Topology topology = loadTopology();

        if (topology == null) { // check if the topology was not loaded
            // topology was not loaded correctly - exit
            return;
        }

        Exporter exporter;
        if (parameters.getAnycastFile() == null) {
            // anycast not activated - use the command unicast exporter
            exporter = new UnicastExporter();
        } else {

            // TODO add progress method for starting to load any cast file

            // anycast is activated - load file and use anycast exporter
            try (AnycastFileReader reader = new AnycastFileReader(parameters.getAnycastFile(), topology)){

                AnycastMap anycastMap = reader.read();
                exporter = new AnycastExporter(anycastMap);

            } catch (IOException | ParseException e) {
                // TODO add error handle method for IO exception with anycast file
                e.printStackTrace();
                return;
            }

            // TODO add progress method for finishing to load any cast file
        }

        // load the engine
        Engine engine = new Engine(new RandomScheduler(parameters.getMinDelay(), parameters.getMaxDelay()), exporter);

        ExecutionStateTracker executionStateTracker = new ExecutionStateTracker(engine);

        // use the simulator factory to get a properly configured simulator
        Simulator simulator = parameters.getSimulatorFactory().getSimulator(
                engine, topology, parameters.getDestinationId());

        if (parameters.isDebugEnabled()) {
            // enable debug
            DebugEventHandler debugEventHandler = new DebugEventHandler(true);
            debugEventHandler.register(engine.getEventGenerator());
        }

        //
        // Execute the simulations and report the results
        //
        try  {
            Reporter reporter = parameters.getReporterFactory().getReporter(
                    parameters.getReportFile(), executionStateTracker);

            reporter.writeBeforeSummary(topology, parameters.getDestinationId(), parameters.getMinDelay(),
                    parameters.getMaxDelay(), parameters.getProtocol(), simulator);

            for (int i = 0; i < parameters.getRepetitionCount(); i++) {
                progressHandler.onStartSimulation(i, parameters);
                simulator.simulate();
                progressHandler.onFinishSimulation(i);

                progressHandler.onStartReporting(i, parameters.getReportFile());
                simulator.getData().report(reporter);
                progressHandler.onFinishReporting(i);
            }

            reporter.writeAfterSummary();

        } catch (IOException e) {
            errorHandler.onReportingIOException(e);
        } finally {
            executionStateTracker.unregister();
        }

        progressHandler.onFinishExecution();
    }

    /**
     * Implements the launcher logic to load the topology. Loads the topology using the reader given by the simulator
     * parameters, calls the progress handler when necessary and calls the error handler to handle IO and Parse
     * exceptions.
     *
     * @return the loaded topology or null if the topology failed to load.
     */
    private Topology loadTopology() {

        Topology topology = null;
        try (TopologyReader topologyReader =
                     parameters.getReaderFactory().getTopologyReader(parameters.getTopologyFile())) {

            progressHandler.onStartLoading(parameters.getTopologyFile());
            topology = topologyReader.read();
            progressHandler.onFinishedLoading(topology);

        } catch (IOException e) {
            errorHandler.onTopologyLoadIOException(e);

        } catch (TopologyParseException e) {
            errorHandler.onTopologyLoadParseException(e);
        }

        return topology;
    }

}
