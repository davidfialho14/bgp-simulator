package main;

import core.Engine;
import core.Protocol;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.TopologyReader;
import io.networkreaders.TopologyReaderFactory;
import io.networkreaders.exceptions.ParseException;
import io.reporters.CSVReporterFactory;
import io.reporters.Reporter;
import io.reporters.ReporterFactory;
import protocols.BGPProtocol;
import simulators.*;

import java.io.File;
import java.io.IOException;


/**
 * The simulator launcher class is responsible for the execution of simulations. The launcher a pre-configured
 * and then its launch() method executes the simulation: loads the topology file, runs all simulations instances,
 * and reports its results. In case of errors it calls the proper methods of the error handler.
 */
public class SimulatorLauncher {

    private static final int DEFAULT_MIN_DELAY = 0;

    private final ErrorHandler errorHandler;
    private final SimulatorConfigurator configuration;

    // basic parameters
    private int minDelay;
    private int maxDelay;
    private int destinationId;
    private int repetitionCount;
    private Protocol protocol;
    private TopologyReaderFactory readerFactory;
    private ReporterFactory reporterFactory;
    private SimulatorFactory simulatorFactory;

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
        this.configuration = new SimulatorConfigurator();
        this.configuration.commit();    // force initializing with default configurations
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Used to start the configuration of the simulator launcher. Returns a basic configurable instance
     * that can be used to configure the simulator.
     *
     * @return a basic configurable instance.
     */
    public BasicConfigurable configure() {
        return configuration;
    }

    /**
     * Starting point of any simulation. This method should be called after configuring the simulation. After calling
     * this method the simulation will load the topology from the topology file, simulate according to the predefined
     * configurations, and dump a proper report.
     */
    public void launch(File topologyFile, File reportFile) {

        // Load the topology

        Topology topology = null;
        try (TopologyReader topologyReader = readerFactory.getTopologyReader(topologyFile)) {
            topology = topologyReader.read();

        } catch (IOException e) {
            errorHandler.onTopologyLoadIOException(e);

        } catch (ParseException e) {
            errorHandler.onTopologyLoadParseException(e);
        }

        if (topology != null) { // if the topology was loaded successfully

            // load the engine
            Engine engine = new Engine(new RandomScheduler(minDelay, maxDelay));

            // use the simulator factory to get a properly configured simulator
            Simulator simulator = simulatorFactory.getSimulator(
                    engine, topology, destinationId);

            try (Reporter reporter = reporterFactory.getReporter(reportFile)) {
                reporter.dumpBasicInfo(topology, destinationId, minDelay,
                        maxDelay, protocol, simulator);

                for (int i = 0; i < repetitionCount; i++) {
                    simulator.simulate();
                    simulator.report(reporter);
                }

            } catch (IOException e) {
                errorHandler.onReportingIOException(e);
            }

        }

    }

    /**
     * Simulator configurator is used to configure the simulator launcher.
     */
    private class SimulatorConfigurator implements
            BasicConfigurable, FullDeploymentConfigurable, GradualDeploymentConfigurable {

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Basic Configuration Parameters - Initialized to their default values
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        private int minDelay = 0;
        private int maxDelay = 10;
        private int destinationId = 0;
        private int repetitionCount = 1;
        private Protocol protocol = new BGPProtocol();
        private TopologyReaderFactory readerFactory = new GraphvizReaderFactory();
        private ReporterFactory reporterFactory = new CSVReporterFactory();

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Full Deployment Configuration Parameters - Disabled by default
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        private boolean fullDeploymentEnabled = false;
        private Integer deployTime = null;

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Full Deployment Configuration Parameters - Disabled by default
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        private boolean gradualDeploymentEnabled = false;
        private Integer deployPeriod = null;
        private Integer deployPercentage = null;

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Configurable methods
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        @Override
        public BasicConfigurable minDelay(int minDelay) {
            this.minDelay = minDelay;
            return this;
        }

        @Override
        public BasicConfigurable maxDelay(int maxDelay) {
            this.maxDelay = maxDelay;
            return this;
        }

        @Override
        public BasicConfigurable destinationId(int destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        @Override
        public BasicConfigurable repetitionCount(int repetitionCount) {
            this.repetitionCount = repetitionCount;
            return this;
        }

        @Override
        public BasicConfigurable protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        @Override
        public BasicConfigurable readerFactory(TopologyReaderFactory readerFactory) {
            this.readerFactory = readerFactory;
            return this;
        }

        @Override
        public BasicConfigurable reporterFactory(ReporterFactory reporterFactory) {
            this.reporterFactory = reporterFactory;
            return this;
        }

        @Override
        public FullDeploymentConfigurable enableFullDeployment(boolean enable) {
            this.fullDeploymentEnabled = enable;
            return this;
        }

        @Override
        public GradualDeploymentConfigurable enableGradualDeployment(boolean enable) {
            this.gradualDeploymentEnabled = enable;
            return this;
        }

        @Override
        public FullDeploymentConfigurable deployTime(int deployTime) {
            this.deployTime = deployTime;
            return this;
        }

        @Override
        public GradualDeploymentConfigurable deployPeriod(int deployPeriod) {
            this.deployPeriod = deployPeriod;
            return this;
        }

        @Override
        public GradualDeploymentConfigurable deployPercentage(int deployPercentage) {
            this.deployPercentage = deployPercentage;
            return this;
        }

        /**
         * Commits the last configuration changes to the simulator launcher.
         * After changing a configuration it only takes affect after committing.
         */
        @Override
        public void commit() {

            SimulatorLauncher.this.minDelay = this.minDelay;
            SimulatorLauncher.this.maxDelay = this.maxDelay;
            SimulatorLauncher.this.destinationId = this.destinationId;
            SimulatorLauncher.this.repetitionCount = this.repetitionCount;
            SimulatorLauncher.this.protocol = this.protocol;
            SimulatorLauncher.this.readerFactory = this.readerFactory;
            SimulatorLauncher.this.reporterFactory = this.reporterFactory;

            if (fullDeploymentEnabled) {
                SimulatorLauncher.this.simulatorFactory = new FullDeploymentSimulatorFactory(protocol, deployTime);
            } else if (gradualDeploymentEnabled) {
                SimulatorLauncher.this.simulatorFactory = new GradualDeploymentSimulatorFactory(
                        protocol, deployPeriod, deployPercentage);
            } else {
                SimulatorLauncher.this.simulatorFactory = new InitialDeploymentSimulatorFactory(protocol);
            }
        }

    }
}
