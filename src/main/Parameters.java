package main;

import core.protocols.Detection;
import io.reporters.CSVReporterFactory;
import io.reporters.ReporterFactory;
import io.topologyreaders.SimpleTopologyReaderFactory;
import io.topologyreaders.TopologyReaderFactory;
import simulators.BasicSetupFactory;
import simulators.SetupFactory;

import java.io.File;

/**
 * The simulator parameters is a container class grouping all necessary parameters to setup the simulator
 * launcher. It is read only and it can only be created using its inner Builder class.
 */
public class Parameters {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Parameters
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final File topologyFile;
    private final TopologyReaderFactory readerFactory;
    private final File reportDestination;
    private final ReporterFactory reporterFactory;
    private final File anycastFile;
    private final int minDelay;
    private final int maxDelay;
    private final int destinationId;
    private final int repetitionCount;
    private final SetupFactory setupFactory;
    private final Long seed;
    private final Integer forcedMRAI;
    private final Detection forcedDetection;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructor - It is a private constructor, instances are created using the Builder class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new parameters object with all the necessary parameters set.
     */
    private Parameters(File topologyFile, TopologyReaderFactory readerFactory, File reportDestination,
                       ReporterFactory reporterFactory, File anycastFile, int minDelay, int maxDelay,
                       int destinationId, int repetitionCount, SetupFactory setupFactory, Long seed,
                       Integer forcedMRAI, Detection forcedDetection) {

        this.topologyFile = topologyFile;
        this.readerFactory = readerFactory;
        this.reportDestination = reportDestination;
        this.reporterFactory = reporterFactory;
        this.anycastFile = anycastFile;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.destinationId = destinationId;
        this.repetitionCount = repetitionCount;
        this.setupFactory = setupFactory;
        this.seed = seed;
        this.forcedMRAI = forcedMRAI;
        this.forcedDetection = forcedDetection;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Getter methods for the parameters fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public File getTopologyFile() {
        return topologyFile;
    }

    public TopologyReaderFactory getReaderFactory() {
        return readerFactory;
    }

    public File getReportDestination() {
        return reportDestination;
    }

    public ReporterFactory getReporterFactory() {
        return reporterFactory;
    }

    public File getAnycastFile() {
        return anycastFile;
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

    public SetupFactory getSetupFactory() {
        return setupFactory;
    }

    public Long getSeed() {
        return seed;
    }

    public boolean hasSeed() {
        return seed != null;
    }

    public Integer forcedMRAI() {
        return forcedMRAI;
    }

    public boolean hasForcedMRAI() {
        return forcedMRAI != null;
    }

    public Detection forcedDetection() {
        return forcedDetection;
    }

    public boolean hasForcedDetection() {
        return forcedDetection != null;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Builder
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
        private final File reportDestination;

        // optional parameters
        private TopologyReaderFactory readerFactory = new SimpleTopologyReaderFactory();
        private ReporterFactory reporterFactory = null;
        private File anycastFile = null;
        private int minDelay = 0;
        private int maxDelay = 10;
        private int destinationId = 0;
        private int repetitionCount = 1;
        private Long seed = null;
        private Integer forcedMRAI = null;
        private Detection forcedDetection = null;

        // parameters for the setup
//        private Integer deployTime = null;
//        private Integer deployPeriod = null;
//        private Integer deployPercentage = null;

        public Builder(File topologyFile, File reportDestination) {
            this.topologyFile = topologyFile;
            this.reportDestination = reportDestination;
        }

        /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
         *
         *  Build methods
         *
         * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

        public Builder readerFactory(TopologyReaderFactory readerFactory) {
            this.readerFactory = readerFactory;
            return this;
        }

        public Builder reporterFactory(ReporterFactory reporterFactory) {
            this.reporterFactory = reporterFactory;
            return this;
        }

        public Builder anycastFile(File anycastFile) {
            this.anycastFile = anycastFile;
            return this;
        }

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

        public Builder seed(Long seed) {
            this.seed = seed;
            return this;
        }

        public Builder forcedMRAI(Integer forcedMRAI) {
            this.forcedMRAI = forcedMRAI;
            return this;
        }

        public Builder forcedDetection(Detection forcedDetection) {
            this.forcedDetection = forcedDetection;
            return this;
        }

        // parameters for the setup
//
//        public Builder timedDeployment(Integer deployTime) {
//            this.deployTime = deployTime;
//            return this;
//        }
//
//        public Builder gradualDeployment(Integer deployPeriod, Integer deployPercentage) {
//            this.deployPeriod = deployPeriod;
//            this.deployPercentage = deployPercentage;
//            return this;
//        }

        public Parameters build() {

            // use by default the CSV reporter
            if (reporterFactory == null) reporterFactory = new CSVReporterFactory(topologyFile, destinationId);

            // TODO implement the selection of the setup factory
            SetupFactory setupFactory = new BasicSetupFactory();

            return new Parameters(topologyFile, readerFactory, reportDestination, reporterFactory,
                    anycastFile, minDelay, maxDelay, destinationId, repetitionCount, setupFactory, seed,
                    forcedMRAI, forcedDetection);
        }

    }


}
