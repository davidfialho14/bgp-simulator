package main;

import core.Protocol;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.TopologyReaderFactory;
import io.reporters.CSVReporterFactory;
import io.reporters.ReporterFactory;
import protocols.BGPProtocol;
import simulators.SimulatorFactory;
import simulators.basic.BasicSimulatorFactory;
import simulators.gradualdeployment.GradualDeploymentSimulatorFactory;
import simulators.timeddeployment.TimedDeploymentSimulatorFactory;

import java.io.File;

/**
 * The simulator parameters is a container class grouping all necessary parameters to setup the simulator launcher.
 * It is read only and it can only be created using its inner Builder class.
 */
public class SimulatorParameters {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Parameters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final File topologyFile;
    private final File reportFile;
    private final int minDelay;
    private final int maxDelay;
    private final int destinationId;
    private final int repetitionCount;
    private final Protocol protocol;
    private final TopologyReaderFactory readerFactory;
    private final ReporterFactory reporterFactory;
    private final SimulatorFactory simulatorFactory;
    private final boolean debugEnabled;
    private final File anycastFile;
    private final Long seed;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructor - It is a private constructor, instances are created using the Builder class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new simulator parameters object with all the necessary parameters set.
     */
    private SimulatorParameters(File topologyFile, File reportFile, int minDelay, int maxDelay, int destinationId,
                                int repetitionCount, Protocol protocol, TopologyReaderFactory readerFactory,
                                ReporterFactory reporterFactory, SimulatorFactory simulatorFactory,
                                boolean debugEnabled, File anycastFile, Long seed) {
        this.topologyFile = topologyFile;
        this.reportFile = reportFile;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.destinationId = destinationId;
        this.repetitionCount = repetitionCount;
        this.protocol = protocol;
        this.readerFactory = readerFactory;
        this.reporterFactory = reporterFactory;
        this.simulatorFactory = simulatorFactory;
        this.debugEnabled = debugEnabled;
        this.anycastFile = anycastFile;
        this.seed = seed;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Getter methods for the parameters fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public File getTopologyFile() {
        return topologyFile;
    }

    public File getReportFile() {
        return reportFile;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public int getRepetitionCount() {
        return repetitionCount;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public TopologyReaderFactory getReaderFactory() {
        return readerFactory;
    }

    public ReporterFactory getReporterFactory() {
        return reporterFactory;
    }

    public SimulatorFactory getSimulatorFactory() {
        return simulatorFactory;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public File getAnycastFile() {
        return anycastFile;
    }

    public boolean hasSeed() {
        return seed != null;
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Builder class used to build the parameters object.
     */
    public static class Builder {

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Parameters - Initialized to their default values
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        // mandatory parameters
        private final File topologyFile;
        private final File reportFile;

        // optional parameters
        private int minDelay = 0;
        private int maxDelay = 10;
        private int destinationId = 0;
        private int repetitionCount = 1;
        private Protocol protocol = new BGPProtocol();
        private TopologyReaderFactory readerFactory = new GraphvizReaderFactory();
        private ReporterFactory reporterFactory = new CSVReporterFactory();
        private Integer deployTime = null;
        private boolean debugEnabled = false;
        private File anycastFile = null;
        private Long seed = null;
        private Integer deployPeriod = null;
        private Integer deployPercentage = null;

        public Builder(File topologyFile, File reportFile) {
            this.topologyFile = topologyFile;
            this.reportFile = reportFile;
        }

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Build methods
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        public Builder minDelay(int minDelay) {
            this.minDelay = minDelay;
            return this;
        }

        public Builder maxDelay(int maxDelay) {
            this.maxDelay = maxDelay;
            return this;
        }

        public Builder destinationId(int destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        public Builder repetitionCount(int repetitionCount) {
            this.repetitionCount = repetitionCount;
            return this;
        }

        public Builder protocol(Protocol protocol) {
            this.protocol = protocol;
            return this;
        }

        public Builder readerFactory(TopologyReaderFactory readerFactory) {
            this.readerFactory = readerFactory;
            return this;
        }

        public Builder reporterFactory(ReporterFactory reporterFactory) {
            this.reporterFactory = reporterFactory;
            return this;
        }

        public Builder timedDeployment(Integer deployTime) {
            this.deployTime = deployTime;
            return this;
        }

        public Builder gradualDeployment(Integer deployPeriod, Integer deployPercentage) {
            this.deployPeriod = deployPeriod;
            this.deployPercentage = deployPercentage;
            return this;
        }

        public Builder debugEnabled(boolean enabled) {
            this.debugEnabled = enabled;
            return this;
        }

        public Builder anycastFile(File anycastFile) {
            this.anycastFile = anycastFile;
            return this;
        }

        public Builder seed(Long seed) {
            this.seed = seed;
            return this;
        }

        public SimulatorParameters build() {

            SimulatorFactory simulatorFactory;

            if (deployTime != null) {
                simulatorFactory = new TimedDeploymentSimulatorFactory(protocol, deployTime);
            } else if (deployPeriod != null && deployPercentage != null) {
                simulatorFactory = new GradualDeploymentSimulatorFactory(
                        protocol, deployPeriod, deployPercentage);
            } else {
                simulatorFactory = new BasicSimulatorFactory(protocol);
            }

            return new SimulatorParameters(topologyFile, reportFile, minDelay, maxDelay, destinationId,
                    repetitionCount, protocol, readerFactory, reporterFactory, simulatorFactory,
                    debugEnabled, anycastFile, seed);
        }

    }


}
