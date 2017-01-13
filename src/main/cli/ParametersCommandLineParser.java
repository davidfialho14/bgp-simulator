package main.cli;


import core.protocols.Detection;
import io.topologyreaders.SimpleTopologyReaderFactory;
import io.topologyreaders.TopologyReaderFactory;
import io.topologyreaders.exceptions.TopologyParseException;
import org.apache.commons.cli.*;

import java.io.File;


/**
 * Parses the command line arguments returning the necessary simulator parameters form it.
 */
public class ParametersCommandLineParser {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Long option names
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static final String TOPOLOGY_FILE = "topology";
    private static final String REPORT_DESTINATION = "report";
    private static final String ANYCAST_FILE = "anycast";
    private static final String DESTINATION = "destination";
    private static final String DESTINATIONS_FILE = "destinations";
    private static final String REPETITION_COUNT = "repetition_count";
    private static final String PERMUTATION_COUNT = "permutation_count";
    private static final String MIN_DELAY = "min_delay";
    private static final String MAX_DELAY = "max_delay";
    private static final String DELAY_SEED = "seed";
    private static final String PERMUTATION_SEED = "perm_seed";
    private static final String MRAI = "MRAI";
    private static final String DETECTION = "detection";
    private static final String THRESHOLD = "threshold";

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Parser and options are static since they are global to all instances of the class
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static final CommandLineParser parser = new DefaultParser();
    private static final Options options = new Options();

    static {
        // setup the command options
        options.addOption("n", TOPOLOGY_FILE, true, "topology to be simulated");
        options.addOption("r", REPORT_DESTINATION, true, "destination for the reports");
        options.addOption("any", ANYCAST_FILE, true, "anycast file to be used");
        options.addOption("dst", DESTINATION, true, "ID of the destination router");
        options.addOption("dsts", DESTINATIONS_FILE, true, "destinations file");
        options.addOption("c", REPETITION_COUNT, true, "number of repetitions");
        options.addOption("p", PERMUTATION_COUNT, true, "number of permutations");
        options.addOption("min", MIN_DELAY, true, "minimum delay (inclusive)");
        options.addOption("max", MAX_DELAY, true, "maximum delay (inclusive)");
        options.addOption("seed", DELAY_SEED, true, "forces the initial seed used for generating delays");
        options.addOption("pseed", PERMUTATION_SEED, true, "forces the seed used for generating permutations");
        options.addOption("MRAI", MRAI, true, "MRAI value to force");
        options.addOption("d", DETECTION, true, "detection method to force (D0 | D1 | D2)");
        options.addOption("th", THRESHOLD, true, "value for the threshold");
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Parses the simulator parameters from the given array with string arguments.
     *
     * @param args  arrays with the string arguments.
     * @return simulator parameters instance with the parsed parameters.
     * @throws ParseException if the arguments are not correct.
     */
    public Parameters parse(String[] args) throws ParseException {
        CommandLine commandLine = parser.parse(options, args);

        File topologyFile = getTopologyFile(commandLine);

        // report file appends the destination to the topology file
        File reportDestination = getReportDestination(commandLine);

        return new Parameters.Builder(topologyFile, reportDestination)
                .readerFactory(getReader(commandLine))
                .anycastFile(getAnycastFile(commandLine))
                .destinationId(getDestinationId(commandLine))
                .destinationsFile(getDestinationsFile(commandLine))
                .repetitionCount(getRepetitionCount(commandLine))
                .permutationCount(getPermutationCount(commandLine))
                .minDelay(getMinDelay(commandLine))
                .maxDelay(getMaxDelay(commandLine))
                .seed(getSeed(commandLine))
                .forcedMRAI(getForcedMRAI(commandLine))
                .forcedDetection(getForcedDetection(commandLine))
                .threshold(getThreshold(commandLine))
                .build();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Parsing methods for each parameter
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Obtains the topology file from the command line. This is not an optional argument, which means that a
     * ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return file instance for the topology file.
     * @throws ParseException if the topology file option is missing.
     */
    private File getTopologyFile(CommandLine commandLine) throws ParseException {
        if (!commandLine.hasOption(TOPOLOGY_FILE)) {
            throw new ParseException(missingOptionMessage("topology file"));
        }

        return new File(commandLine.getOptionValue(TOPOLOGY_FILE));
    }

    /**
     * Obtains the report destination from the command line. This is not an optional argument, which means
     * that a ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination of the report (might be a file or a directory).
     * @throws ParseException if the topology file option is missing.
     */
    private File getReportDestination(CommandLine commandLine) throws ParseException {
        if (!commandLine.hasOption(REPORT_DESTINATION)) {
            throw new ParseException(missingOptionMessage("report destination"));
        }

        return new File(commandLine.getOptionValue(REPORT_DESTINATION));
    }

    /**
     * Obtains the input format of the topology file from the command line and returns the appropriate reader
     * factory. This is an optional argument, by default returns the simple topology reader.
     *
     * @param commandLine command line containing the parsed options.
     * @return reader factory instance.
     */
    private TopologyReaderFactory getReader(CommandLine commandLine) {
        return new SimpleTopologyReaderFactory();
    }

    /**
     * Obtains the anycast file from teh command line. This is an optional argument, in case it is missing null will
     * be returned.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed anycast file or null if the argument does not exist.
     */
    private File getAnycastFile(CommandLine commandLine) {
        if (commandLine.hasOption(ANYCAST_FILE)) {
            return new File(commandLine.getOptionValue(ANYCAST_FILE));
        } else {
            return null;
        }
    }

    /**
     * Obtains the destination ID from the command line. This is an optional argument.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination ID in integer format.
     * @throws ParseException if the destination option is not an integer.
     */
    private Integer getDestinationId(CommandLine commandLine) throws ParseException {
        return getOptionalIntegerParameter(commandLine, DESTINATION, "destination ID");
    }

    /**
     * Obtains the destination file from the command line. This is an optional argument.
     *
     * @param commandLine command line containing the parsed options.
     * @return destinations file path or null if option not set.
     */
    private File getDestinationsFile(CommandLine commandLine) {

        File destinationsFile = null;
        if (commandLine.hasOption(DESTINATIONS_FILE)) {
            destinationsFile = new File(commandLine.getOptionValue(DESTINATIONS_FILE));
        }

        return destinationsFile;
    }

    /**
     * Obtains the repetition count from the command line. This is an optional argument.
     *
     * @param commandLine command line containing the parsed options.
     * @return repetition count or null if not specified.
     * @throws ParseException if the repetitions count option is not an integer.
     */
    private Integer getRepetitionCount(CommandLine commandLine) throws ParseException {
        return getOptionalIntegerParameter(commandLine, REPETITION_COUNT, "repetition count");
    }

    /**
     * Obtains the permutation count from the command line. This is an optional argument.
     *
     * @param commandLine command line containing the parsed options.
     * @return permutation count or null if not specified.
     * @throws ParseException if the repetitions count option is not an integer.
     */
    private Integer getPermutationCount(CommandLine commandLine) throws ParseException {
        return getOptionalIntegerParameter(commandLine, PERMUTATION_COUNT, "permutation count");
    }

    /**
     * Obtains the minimum delay value from the command line. This is not an optional argument, which means
     * that a ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination ID in integer format.
     * @throws ParseException if the minimum delay value option is missing or is not an integer.
     */
    private int getMinDelay(CommandLine commandLine) throws ParseException {

        if (!commandLine.hasOption(MIN_DELAY)) {
            throw new ParseException(missingOptionMessage("minimum delay value"));
        }

        try {
            return Integer.parseInt(commandLine.getOptionValue(MIN_DELAY));
        } catch (NumberFormatException e){
            throw new ParseException(expectedIntegerMessage("minimum delay value"));
        }

    }

    /**
     * Obtains the maximum delay value from the command line. This is not an optional argument, which means
     * that a ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination ID in integer format.
     * @throws ParseException if the maximum delay value option is missing or is not an integer.
     */
    private int getMaxDelay(CommandLine commandLine) throws ParseException {

        if (!commandLine.hasOption(MAX_DELAY)) {
            throw new ParseException(missingOptionMessage("maximum delay value"));
        }

        try {
            return Integer.parseInt(commandLine.getOptionValue(MAX_DELAY));
        } catch (NumberFormatException e){
            throw new ParseException(expectedIntegerMessage("maximum delay value"));
        }

    }

    /**
     * Obtains the seed value from the command line. This is an optional argument, in case it is missing null will
     * be returned. It throws a parse exception if the option is available but the argument value is not a signed long.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed anycast file or null if the argument does not exist.
     * @throws ParseException if the option is available but the argument value is not a signed
     * long or if both the "DELAY_SEED" and "SEEDS" options are enabled.
     */
    private Long getSeed(CommandLine commandLine) throws ParseException {

        if (commandLine.hasOption(DELAY_SEED)) {

            try {
                return Long.parseLong(commandLine.getOptionValue(DELAY_SEED));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage("seed"));
            }

        } else {
            return null;
        }
    }

    /**
     * Obtains the permutation seed value from the command line. This is an optional argument, in case it
     * is missing null will be returned. It throws a parse exception if the option is available but the
     * argument value is not a signed long.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed permutation seed value or null if the argument does not exist.
     * @throws ParseException if the option is available but the argument value is not a signed long.
     */
    private Long getPermutationSeed(CommandLine commandLine) throws ParseException {

        if (commandLine.hasOption(PERMUTATION_SEED)) {

            try {
                return Long.parseLong(commandLine.getOptionValue(PERMUTATION_SEED));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage("permutation seed"));
            }

        } else {
            return null;
        }
    }

    /**
     * Obtains the forced MRAI value from the command line. This is an optional argument, in case it is
     * missing null will be returned. It throws a parse exception if the option is available but the
     * argument value is not a signed long.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed forced MRAI or null if the argument does not exist.
     * @throws ParseException if the option is available but the argument value is not a signed integer.
     */
    private Integer getForcedMRAI(CommandLine commandLine) throws ParseException {
        return getOptionalIntegerParameter(commandLine, MRAI, "forced MRAI");
    }

    /**
     * Obtains the detection value from the command line. This is an optional argument, in case it is
     * missing null will be returned. It throws a parse exception if the option is available but the
     * argument value is not a signed long.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed detection or null if the argument does not exist.
     * @throws ParseException if the option is available but the argument value is not a valid detection tag.
     */
    private Detection getForcedDetection(CommandLine commandLine) throws ParseException {

        if (commandLine.hasOption(DETECTION)) {
            try {
                return Detection.parseDetection(commandLine.getOptionValue(DETECTION));
            } catch (TopologyParseException e) {
                throw new ParseException(String.format("'%s' is not a valid detection tag",
                        commandLine.getOptionValue(DETECTION)));
            }

        } else {
            return null;
        }
    }

    /**
     * Obtains the threshold value from the command line. This is an optional argument, in case it is
     * missing null will be returned. It throws a parse exception if the option is available but the
     * argument value is not a signed long.
     *
     * @param commandLine command line containing the parsed options.
     * @return the parsed detection or null if the argument does not exist.
     * @throws ParseException if the option is available but the argument value is not a valid detection tag.
     */
    private Integer getThreshold(CommandLine commandLine) throws ParseException {
        return getOptionalIntegerParameter(commandLine, THRESHOLD, "threshold");
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Helper method to create common error messages
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Integer getOptionalIntegerParameter(CommandLine commandLine, String parameter, String optionName)
            throws ParseException {

        if (commandLine.hasOption(parameter)) {
            try {
                return Integer.parseInt(commandLine.getOptionValue(parameter));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage(optionName));
            }

        } else {
            return null;
        }

    }

    /**
     * Returns the formatted message for missing non-optional arguments.
     *
     * @param optionName name for the option to display in the message.
     * @return the formatted message for missing non-optional arguments.
     */
    private String missingOptionMessage(String optionName) {
        return optionName + " is missing and it is not an optional argument";
    }

    /**
     * Returns the formatted message for an argument was expected to be an integer value but it is not.
     *
     * @param optionName name for the option to display in the message.
     * @return the formatted message error.
     */
    private String expectedIntegerMessage(String optionName) {
        return optionName + " must be an integer value";
    }

}
