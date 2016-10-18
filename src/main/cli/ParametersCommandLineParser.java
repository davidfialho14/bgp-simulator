package main.cli;

import core.Protocol;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.SimpleTopologyReaderFactory;
import io.networkreaders.TopologyReaderFactory;
import io.reporters.CSVReporterFactory;
import main.SimulatorParameters;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;
import protocols.*;
import simulators.SimulatorFactory;
import simulators.basic.BasicSimulatorFactory;
import simulators.timeddeployment.TimedDeploymentSimulatorFactory;

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
    private static final String DESTINATION = "destination";
    private static final String REPETITION_COUNT = "repetition_count";
    private static final String ENABLED_DETECTION = "detection";
    private static final String ENABLED_REACTION = "reaction";
    private static final String ENABLED_BGP = "bgp";
    private static final String DEPLOY_TIME = "deploy_time";
    private static final String INPUT_FORMAT_GRAPHVIZ = "graphviz";
    private static final String DEBUG = "debug";
    private static final String ANYCAST_FILE = "anycast";
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
        options.addOption("d", DESTINATION, true, "simulate with the given destination id");
        options.addOption("c", REPETITION_COUNT, true, "number of repetitions");
        options.addOption("dt", ENABLED_DETECTION, true, "use other detection instead of detection 1");
        options.addOption("rt", ENABLED_DETECTION, true, "use other reaction instead of reaction 1");
        options.addOption("t", DEPLOY_TIME, true, "time deploy detection");
        options.addOption("gv", INPUT_FORMAT_GRAPHVIZ, false, "indicate the input network file is in Graphviz format");
        options.addOption("debug", DEBUG, false, "activate debugging");
        options.addOption("any", ANYCAST_FILE, true, "anycast file to be used");
        options.addOption("bgp", ENABLED_BGP, false, "use the original BGP");
        options.addOption("s", SEED, true, "seed value to force");
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
    public SimulatorParameters parse(String[] args) throws ParseException {
        CommandLine commandLine = parser.parse(options, args);

        int destinationId = getDestinationId(commandLine);
        File topologyFile = getTopologyFile(commandLine);

        // report file appends the destination to the topology file
        File reportFile = new File(topologyFile.getParent(),
                FilenameUtils.getBaseName(topologyFile.getName()) + String.format("-dest%02d", destinationId));

        return new SimulatorParameters.Builder(topologyFile, reportFile)
                .readerFactory(getReader(commandLine))
                .destinationId(destinationId)
                .repetitionCount(getRepetitionCount(commandLine))
                .protocol(getProtocol(commandLine))
                .simulatorFactory(getSimulatorFactory(commandLine))
                .reporterFactory(new CSVReporterFactory())
                .debugEnabled(isDebugEnabled(commandLine))
                .anycastFile(getAnycastFile(commandLine))
                .seed(getSeed(commandLine))
                .build();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Parsing methods for each parameter
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Obtains the input format of the topology file from the command line and returns the appropriate reader
     * factory. This is an optional argument.
     *
     * @param commandLine command line containing the parsed options.
     * @return reader factory instance.
     */
    private TopologyReaderFactory getReader(CommandLine commandLine) {
        if (commandLine.hasOption(INPUT_FORMAT_GRAPHVIZ)) {
            return new GraphvizReaderFactory();
        } else {
            // by default use simple reader
            return new SimpleTopologyReaderFactory();
        }
    }

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
     * Obtains the protocol from the command line. This is an optional argument, if not specified by the user it
     * returns the default protocol (instance of D1R1Protocol).
     *
     * @param commandLine command line containing the parsed options
     * @return a new protocol instance.
     */
    private Protocol getProtocol(CommandLine commandLine) throws ParseException {
        if (commandLine.hasOption(ENABLED_BGP)) {
            return new BGPProtocol();
        }

        // By default use the simple detection and the cut-off reaction
        Detection detection = new SimpleDetection();
        Reaction reaction = new CutOffReaction();

        if (commandLine.hasOption(ENABLED_DETECTION)) {
            int detectionId;
            try {
                detectionId = Integer.parseInt(commandLine.getOptionValue(ENABLED_DETECTION));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage("detection"));
            }

            switch (detectionId) {
                case 1:
                    detection = new SimpleDetection();
                    break;
                case 2:
                    detection = new PathDetection();
                    break;
                case 3:
                    detection = new NeighborDetection();
                    break;
                default:
                    throw new ParseException(String.format("Unknown detection '%d'", detectionId));
            }
        }

        if (commandLine.hasOption(ENABLED_REACTION)) {
            int reactionId;
            try {
                reactionId = Integer.parseInt(commandLine.getOptionValue(ENABLED_REACTION));
            } catch (NumberFormatException e) {
                throw new ParseException(expectedIntegerMessage("reaction"));
            }

            switch (reactionId) {
                case 1:
                    reaction = new CutOffReaction();
                    break;
                default:
                    throw new ParseException(String.format("Unknown reaction '%d'", reactionId));
            }
        }

        return new GenericProtocol(detection, reaction);
    }

    /**
     * Obtains the simulator factory implementation based on the available options in the command line.
     *
     * @param commandLine command line containing the parsed options.
     * @return a new simulator factory instance.
     * @throws ParseException if the deploy time is not an integer value.
     */
    private SimulatorFactory getSimulatorFactory(CommandLine commandLine) throws ParseException {

        if (commandLine.hasOption(DEPLOY_TIME)) {
            return new TimedDeploymentSimulatorFactory(getProtocol(commandLine), getDeployTime(commandLine));
        } else {
            return new BasicSimulatorFactory(getProtocol(commandLine));
        }
    }

    /**
     * Obtains the deploy time argument from the command line in integer format.
     *
     * @param commandLine command line containing the parsed options.
     * @return a new simulator factory instance.
     * @throws ParseException if the deploy time is not an integer.
     */
    private int getDeployTime(CommandLine commandLine) throws ParseException {

        try {
            return Integer.parseInt(commandLine.getOptionValue(DEPLOY_TIME));
        } catch (NumberFormatException e) {
            throw new ParseException(expectedIntegerMessage("deploy time"));
        }
    }

    /**
     * Checks if the command line contains the -debug option flag.
     *
     * @param commandLine command line containing the parsed options.
     * @return true if the -debug is present and false otherwise
     */
    private boolean isDebugEnabled(CommandLine commandLine) {
        return commandLine.hasOption(DEBUG);
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
