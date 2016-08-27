package main.cli;

import core.Protocol;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.SimpleTopologyReaderFactory;
import io.networkreaders.TopologyReaderFactory;
import io.reporters.CSVReporterFactory;
import main.SimulatorParameters;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
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
    private static final String ENABLED_DETECTION2 = "detection2";
    private static final String DEPLOY_TIME = "deploy_time";
    private static final String INPUT_FORMAT_GRAPHVIZ = "graphviz";

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
        options.addOption("d2", ENABLED_DETECTION2, false, "use detection 2 instead of detection 1");
        options.addOption("t", DEPLOY_TIME, true, "time deploy detection");
        options.addOption("gv", INPUT_FORMAT_GRAPHVIZ, true, "indicate the input network file is in Graphviz format");
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
                .readerFactory(gerReader(commandLine))
                .destinationId(destinationId)
                .repetitionCount(getRepetitionCount(commandLine))
                .protocol(getProtocol(commandLine))
                .simulatorFactory(getSimulatorFactory(commandLine))
                .reporterFactory(new CSVReporterFactory())
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
    private TopologyReaderFactory gerReader(CommandLine commandLine) {
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
    private Protocol getProtocol(CommandLine commandLine) {
        if (commandLine.hasOption(ENABLED_DETECTION2)) {
            return new D2R1Protocol();
        } else {
            return new D1R1Protocol();
        }
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
