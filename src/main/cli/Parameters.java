package main.cli;

import core.protocols.Detection;
import io.topologyreaders.SimpleTopologyReaderFactory;
import io.topologyreaders.TopologyReaderFactory;
import org.apache.commons.cli.ParseException;

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
    private final File anycastFile;
    private final int minDelay;
    private final int maxDelay;
    private final Integer destinationId;
    private final File destinationsFile;
    private final Integer repetitionCount;
    private final Integer permutationCount;
    private final Long seed;
    private Long permutationSeed;
    private final Integer forcedMRAI;
    private final Detection forcedDetection;
    private final int threshold;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructor - It is a private constructor, instances are created using the Builder class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new parameters object with all the necessary parameters set.
     */
    private Parameters(File topologyFile, TopologyReaderFactory readerFactory, File reportDestination,
                       File anycastFile, int minDelay, int maxDelay,
                       Integer destinationId, File destinationsFile, Integer repetitionCount, Integer permutationCount, Long seed,
                       Long permutationSeed, Integer forcedMRAI, Detection forcedDetection, int threshold) {

        this.topologyFile = topologyFile;
        this.readerFactory = readerFactory;
        this.reportDestination = reportDestination;
        this.anycastFile = anycastFile;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.destinationId = destinationId;
        this.destinationsFile = destinationsFile;
        this.repetitionCount = repetitionCount;
        this.permutationCount = permutationCount;
        this.seed = seed;
        this.permutationSeed = permutationSeed;
        this.forcedMRAI = forcedMRAI;
        this.forcedDetection = forcedDetection;
        this.threshold = threshold;
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

    public File getAnycastFile() {
        return anycastFile;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public Integer getDestinationId() {
        return destinationId;
    }

    public File getDestinationsFile() {
        return destinationsFile;
    }

    public Integer getRepetitionCount() {
        return repetitionCount;
    }

    public Integer getPermutationCount() {
        return permutationCount;
    }

    public Long getSeed() {
        return seed;
    }

    public boolean hasSeed() {
        return seed != null;
    }

    public Long getPermutationSeed() {
        return permutationSeed;
    }

    public boolean hasPermutationSeed() {
        return permutationSeed != null;
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

    public int getThreshold() {
        return threshold;
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
        private File anycastFile = null;
        private int minDelay = 0;
        private int maxDelay = 10;
        private Integer destinationId = null;
        private File destinationsFile = null;
        private Integer repetitionCount = 1;
        private Integer permutationCount = 1;
        private Long seed = null;
        private Long permutationSeed = null;
        private Integer forcedMRAI = null;
        private Detection forcedDetection = null;
        private int threshold = Integer.MAX_VALUE;

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

        public Builder destinationId(Integer destinationId) {
            this.destinationId = destinationId;
            return this;
        }

        public Builder destinationsFile(File destinationsFile) {
            this.destinationsFile = destinationsFile;
            return this;
        }

        public Builder repetitionCount(Integer repetitionCount) {
            if (repetitionCount != null)
                this.repetitionCount = repetitionCount;

            return this;
        }

        public Builder permutationCount(Integer permutationCount) {
            if (permutationCount != null)
                this.permutationCount = permutationCount;

            return this;
        }

        public Builder seed(Long seed) {
            this.seed = seed;
            return this;
        }

        public Builder permutationSeed(Long seed) {
            this.permutationSeed = seed;
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

        public Builder threshold(Integer threshold) {
            if (threshold != null)
                this.threshold = threshold;

            return this;
        }

        public Parameters build() throws ParseException {

            if (destinationsFile == null && destinationId == null) {
                throw new ParseException("Missing both the destinations file and the destination ID");
            }

            return new Parameters(topologyFile, readerFactory, reportDestination,
                    anycastFile, minDelay, maxDelay, destinationId, destinationsFile, repetitionCount,
                    permutationCount, seed, permutationSeed, forcedMRAI, forcedDetection, threshold);
        }

    }


}
