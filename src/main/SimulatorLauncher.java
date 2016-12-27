package main;

import core.Destination;
import core.Engine;
import core.Router;
import core.Topology;
import core.exporters.BasicExporter;
import core.schedulers.RandomScheduler;
import core.schedulers.Scheduler;
import io.AnycastParser;
import io.DestinationNotFoundException;
import io.reporters.Reporter;
import io.topologyreaders.TopologyReader;
import io.topologyreaders.TopologyReaderFactory;
import io.topologyreaders.exceptions.TopologyParseException;
import simulators.Simulator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The simulator launcher class is responsible for the execution of simulations. The launcher a pre-configured
 * and then its launch() method executes the simulation: loads the topology file, runs all simulations instances,
 * and reports its results. In case of errors it calls the proper methods of the error handler.
 */
public class SimulatorLauncher {

    private final ErrorHandler errorHandler;
    private final ProgressHandler progressHandler;

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

    /**
     * Starting point of any simulation. This method should be called after configuring the simulation.
     * After calling this method the simulation will load the topology from the topology file, simulate
     * according to the predefined configurations, and write a proper report.
     */
    public int launch(Parameters parameters) {

        // Load topology
        Topology topology = loadTopology(parameters);
        if (topology == null) return 1;

        // Get the destination from the topology or the anycast file
        Destination destination = getDestination(parameters.getAnycastFile(), topology,
                                                 parameters.getDestinationId());
        if (destination == null) return 1;

        //
        // Setup scheduler
        //
        Scheduler scheduler;
        if (parameters.hasSeed()) {
            scheduler = new RandomScheduler(parameters.getMinDelay(), parameters.getMaxDelay(),
                                            parameters.getSeed());
        } else {
            scheduler = new RandomScheduler(parameters.getMinDelay(), parameters.getMaxDelay());
        }

        // Setup the engine
        Engine engine = new Engine(new BasicExporter(scheduler), parameters.getThreshold());

        // use the simulator factory to get a properly configured simulator
        Simulator simulator = new Simulator(parameters.getSetupFactory().getSetup(engine, topology, destination));

        return simulate(simulator, parameters);
    }

    /**
     * Implements the launcher logic to load the topology. Loads the topology using the reader given by the simulator
     * parameters, calls the progress handler when necessary and calls the error handler to handle IO and Parse
     * exceptions.
     *
     * @return the loaded topology or null if the topology failed to load.
     */
    private Topology loadTopology(Parameters parameters) {

        Topology topology = null;
        TopologyReaderFactory readerFactory = parameters.getReaderFactory();

        try (TopologyReader topologyReader = readerFactory.getTopologyReader(parameters.getTopologyFile())) {

            progressHandler.onStartLoadingTopology(parameters.getTopologyFile());
            topology = topologyReader.read();
            progressHandler.onFinishedLoadingTopology(topology);

            if (parameters.hasForcedMRAI()) {
                for (Router router : topology.getRouters()) {
                    router.getMRAITimer().setMRAI(parameters.forcedMRAI());
                }
            }

            if (parameters.hasForcedDetection()) {
                for (Router router : topology.getRouters()) {
                    router.setDetection(parameters.forcedDetection());
                }
            }

        } catch (IOException e) {
            errorHandler.onTopologyLoadIOException(e);

        } catch (TopologyParseException e) {
            errorHandler.onTopologyLoadParseException(e);
        }

        return topology;
    }

    private Destination getDestination(File anycastFile, Topology topology, int destinationID) {

        // first try to find destination in the topology
        Destination destination = topology.getRouter(destinationID);

        if (destination != null) return destination;

        if (anycastFile == null) {
            errorHandler.onUnknownDestination(destinationID);
            return null;
        }

        // not in the topology lets look in the anycast file

        try {
            destination = AnycastParser.parseDestination(anycastFile.getPath(), topology, destinationID);

        } catch (DestinationNotFoundException e) {
            errorHandler.onDestinationNotFoundOnAnycastFile(e, anycastFile, destinationID);
            return null;

        } catch (IOException e) {
            errorHandler.onAnycastLoadIOException(e);
            return null;
        }

        return destination;
    }

    private List<Long> loadSeeds(File seedFile) {

        progressHandler.onStartLoadingSeedsFile(seedFile);
        try (Stream<String> stream = Files.lines(Paths.get(seedFile.getPath()))) {
            List<Long> seeds = stream
                    .filter(line -> !line.isEmpty())
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            progressHandler.onFinishedLoadingSeedsFile(seeds);

            return seeds;

        } catch (IOException e) {
            errorHandler.onSeedsFileLoadIOException(e);
            return null;
        }

    }

    private int simulate(Simulator simulator, Parameters parameters) {

        progressHandler.onStartExecution();

        try  {
            Reporter reporter = parameters.getReporterFactory().getReporter(parameters.getReportDestination());

            simulator.getSetup().report(reporter, parameters);

            for (int i = 0; i < parameters.getRepetitionCount(); i++) {
                progressHandler.onStartSimulation(i, parameters);
                simulator.simulate();
                progressHandler.onFinishSimulation(i);

                progressHandler.onStartReporting(i, parameters.getReportDestination());
                simulator.report(reporter, i + 1);
                progressHandler.onFinishReporting(i);
            }

            reporter.writeAfterSummary();

        } catch (IOException e) {
            errorHandler.onReportingIOException(e);
            return -1;
        }

        progressHandler.onFinishExecution();
        return 0;
    }

}
