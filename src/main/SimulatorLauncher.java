package main;

import core.Engine;
import core.Protocol;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.networkreaders.TopologyReader;
import io.networkreaders.exceptions.ParseException;
import io.reporters.Reporter;
import simulators.*;

import java.io.IOException;

public class SimulatorLauncher {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Configuration Fields - Not Optional
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final ErrorHandler errorHandler;

    private final int minDelay;
    private final int maxDelay;
    private final int destinationId;
    private final int repetitionCount;
    private final Protocol protocol;
    private final SimulatorFactory simulatorFactory;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private SimulatorLauncher(ErrorHandler errorHandler, int minDelay, int maxDelay, int destinationId,
                              int repetitionCount, Protocol protocol, SimulatorFactory simulatorFactory) {
        this.errorHandler = errorHandler;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.destinationId = destinationId;
        this.repetitionCount = repetitionCount;
        this.protocol = protocol;
        this.simulatorFactory = simulatorFactory;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    // IMPORTANT: the launch() method takes the topology reader and the reporter as an arguments instead of using the
    //            set methods (or the constructor) to configure the proper reader and reporter. This allows to use a
    //            try block to ensure the topology reader/reporter are closed properly.

    /**
     * Starting point of any simulation. This method should be called after configuring the simulation. After calling
     * this method the simulation will load the topology from the topology file, simulate according to the predefined
     * configurations, and dump a proper report.
     *
     * @param topologyReader topology reader used to load the topology
     * @param reporter       a reporter implementation to report data
     */
    public void launch(TopologyReader topologyReader, Reporter reporter) {

        // Load the topology

        Topology topology = null;
        try {
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

            try {
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

        // optional configurations
        private boolean fullDeploymentEnabled = false;
        private boolean gradualDeploymentEnabled = false;
        private Integer deployTime = null;
        private Integer deployPeriod = null;
        private Integer deployPercentage = null;

        public Builder(ErrorHandler errorHandler, int minDelay, int maxDelay, int destinationId, int repetitionCount,
                       Protocol protocol) {
            this.errorHandler = errorHandler;
            this.minDelay = minDelay;
            this.maxDelay = maxDelay;
            this.destinationId = destinationId;
            this.repetitionCount = repetitionCount;
            this.protocol = protocol;
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

            return new SimulatorLauncher(errorHandler, minDelay, maxDelay, destinationId,
                    repetitionCount, protocol, simulatorFactory);
        }

    }
}
