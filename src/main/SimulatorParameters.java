package main;

import core.Protocol;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.TopologyReaderFactory;
import io.newreporters.CSVReporterFactory;
import io.newreporters.ReporterFactory;
import newsimulators.SimulatorFactory;
import protocols.BGPProtocol;

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
                                ReporterFactory reporterFactory, SimulatorFactory simulatorFactory) {
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
        private SimulatorFactory simulatorFactory;

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

        public Builder simulatorFactory(SimulatorFactory simulatorFactory) {
            this.simulatorFactory = simulatorFactory;
            return this;
        }

        public SimulatorParameters build() {
            return new SimulatorParameters(topologyFile, reportFile, minDelay, maxDelay, destinationId, repetitionCount, protocol,
                    readerFactory, reporterFactory, simulatorFactory);
        }

    }


}
