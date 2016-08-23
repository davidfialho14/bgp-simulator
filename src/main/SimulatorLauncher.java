package main;

import core.Engine;
import core.Protocol;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.networkreaders.TopologyReader;
import io.networkreaders.TopologyReaderFactory;
import io.networkreaders.exceptions.ParseException;
import io.reporters.Reporter;
import io.reporters.ReporterFactory;
import simulators.*;

import java.io.File;
import java.io.IOException;

public class SimulatorLauncher {

    private final ErrorHandler errorHandler;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Configuration Fields - Not Optional
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private final TopologyReaderFactory readerFactory;
    private final int minDelay;
    private final int maxDelay;
    private final int destinationId;
    private final int repetitionCount;
    private final Protocol protocol;
    private final SimulatorFactory simulatorFactory;
    private final ReporterFactory reporterFactory;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors - Simulator Launcher is created with a Builder
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private SimulatorLauncher(ErrorHandler errorHandler, TopologyReaderFactory readerFactory,
                              int minDelay, int maxDelay, int destinationId, int repetitionCount, Protocol protocol,
                              SimulatorFactory simulatorFactory, ReporterFactory reporterFactory) {
        this.errorHandler = errorHandler;
        this.readerFactory = readerFactory;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.destinationId = destinationId;
        this.repetitionCount = repetitionCount;
        this.protocol = protocol;
        this.simulatorFactory = simulatorFactory;
        this.reporterFactory = reporterFactory;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
            Simulator simulator = simulatorFactory.getSimulator(engine, topology, destinationId);

            try (Reporter reporter = reporterFactory.getReporter(reportFile)) {
                reporter.dumpBasicInfo(topology, destinationId, minDelay, maxDelay, protocol, simulator);

                for (int i = 0; i < repetitionCount; i++) {
                    simulator.simulate();
                    simulator.report(reporter);
                }

            } catch (IOException e) {
                // let the subclass handle the error
                errorHandler.onReportingIOException(e);
            }

        }

    }

    /**
     * Builder class for the simulator launcher.
     */
    public static class Builder {

        // non optional configurations
        private final ErrorHandler errorHandler;
        private final int minDelay;
        private final int maxDelay;
        private final int destinationId;
        private final int repetitionCount;
        private final Protocol protocol;
        private final TopologyReaderFactory readerFactory;
        private final ReporterFactory reporterFactory;

        // optional configurations
        private boolean fullDeploymentEnabled = false;
        private boolean gradualDeploymentEnabled = false;
        private Integer deployTime = null;
        private Integer deployPeriod = null;
        private Integer deployPercentage = null;

        public Builder(ErrorHandler errorHandler, TopologyReaderFactory readerFactory, int minDelay, int maxDelay,
                       int destinationId, int repetitionCount, Protocol protocol, ReporterFactory reporterFactory) {
            this.errorHandler = errorHandler;
            this.readerFactory = readerFactory;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
            this.destinationId = destinationId;
            this.repetitionCount = repetitionCount;
            this.protocol = protocol;
            this.reporterFactory = reporterFactory;
        }

        public Builder fullDeployment(boolean enable, int deployTime) {
            this.fullDeploymentEnabled = enable;
            this.deployTime = deployTime;

            return this;
        }

        public Builder gradualDeployment(boolean enable, int deployPeriod,
                                         int deployPercentage) {
            this.gradualDeploymentEnabled = enable;
            this.deployPeriod = deployPeriod;
            this.deployPercentage = deployPercentage;

            return this;
        }

        public SimulatorLauncher build() {

            SimulatorFactory simulatorFactory;
            if (fullDeploymentEnabled) {
                simulatorFactory = new FullDeploymentSimulatorFactory(protocol, deployTime);
            } else if (gradualDeploymentEnabled) {
                simulatorFactory = new GradualDeploymentSimulatorFactory(protocol, deployPeriod,
                        deployPercentage);
            } else {
                simulatorFactory = new InitialDeploymentSimulatorFactory(protocol);
            }

            return new SimulatorLauncher(errorHandler, readerFactory, minDelay, maxDelay, destinationId,
                    repetitionCount, protocol, simulatorFactory, reporterFactory);
        }

    }
}
