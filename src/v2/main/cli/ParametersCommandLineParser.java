package v2.main.cli;


import org.apache.commons.cli.*;
import v2.io.topologyreaders.SimpleTopologyReaderFactory;
import v2.io.topologyreaders.TopologyReaderFactory;
import v2.main.Parameters;

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
    private static final String REPETITION_COUNT = "repetition_count";
    private static final String MIN_DELAY = "min_delay";
    private static final String MAX_DELAY = "max_delay";
    private static final String SEED = "seed";

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
        options.addOption("c", REPETITION_COUNT, true, "number of repetitions");
        options.addOption("min", MIN_DELAY, true, "minimum delay (inclusive)");
        options.addOption("max", MAX_DELAY, true, "maximum delay (inclusive)");
        options.addOption("seed", SEED, true, "seed value to force");
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
                .repetitionCount(getRepetitionCount(commandLine))
                .minDelay(getMinDelay(commandLine))
                .maxDelay(getMaxDelay(commandLine))
                .seed(getSeed(commandLine))
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
     * Obtains the destination ID from the command line. This is not an optional argument, which means that a
     * ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination ID in integer format.
     * @throws ParseException if the destination option is missing or is not an integer.
     */
    private int getDestinationId(CommandLine commandLine) throws ParseException {
        if (!commandLine.hasOption(DESTINATION)) {
            throw new ParseException(missingOptionMessage("destination ID"));
        }

        try {
            return Integer.parseInt(commandLine.getOptionValue(DESTINATION));
        } catch (NumberFormatException e){
            throw new ParseException(expectedIntegerMessage("destination ID"));
        }

    }

    /**
     * Obtains the destination ID from the command line. This is not an optional argument, which means that a
     * ParseException is thrown when the argument is missing.
     *
     * @param commandLine command line containing the parsed options.
     * @return destination ID in integer format.
     * @throws ParseException if the repetitions count option is missing or is not an integer.
     */
    private int getRepetitionCount(CommandLine commandLine) throws ParseException {
        if (!commandLine.hasOption(REPETITION_COUNT)) {
            throw new ParseException(missingOptionMessage("number of repetitions"));
        }

        try {
            return Integer.parseInt(commandLine.getOptionValue(REPETITION_COUNT));
        } catch (NumberFormatException e){
            throw new ParseException(expectedIntegerMessage("number of repetitions"));
        }
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
     * @throws ParseException if the option is available but the argument value is not a signed long.
     */
    private Long getSeed(CommandLine commandLine) throws ParseException {

        if (commandLine.hasOption(SEED)) {
            try {
                return Long.parseLong(commandLine.getOptionValue(SEED));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage("seed"));
            }
        } else {
            return null;
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Helper method to create common error messages
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

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
