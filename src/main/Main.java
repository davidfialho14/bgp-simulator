package main;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.TokenMgrError;
import gui.SimulatorApplication;
import io.InvalidTagException;
import io.NetworkParser;
import io.reporters.CSVReporter;
import io.reporters.Reporter;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import protocols.Protocol;
import simulation.Engine;
import simulation.schedulers.RandomScheduler;
import simulators.Simulator;
import simulators.SimulatorFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("d", "destination", true, "simulate with the given destination id");
        options.addOption("n", "network", true, "network to be simulated");
        options.addOption("d2", "use detection 2 instead of detection 1");

        // parse the command line arguments
        try {
            CommandLine cmd = parser.parse(options, args);

            File networkFile = null;
            if (cmd.hasOption("network")) {
                networkFile = new File(cmd.getOptionValue("network"));

                if (!networkFile.exists()) {
                    System.err.println("Network file does not exist");
                    System.exit(1);
                }
            }

            if (cmd.hasOption("destination")) {
                // execute directly
                int destinationId = Integer.parseInt(cmd.getOptionValue("destination"));

                if (networkFile == null) {
                    System.err.println("It is missing the network file");
                    System.exit(1);
                }

                Protocol protocol;
                if (cmd.hasOption("d2")) {
                    protocol = new D2R1Protocol();
                } else {
                    protocol = new D1R1Protocol();
                }

                simulate(networkFile, destinationId, protocol);

            } else {
                // display the GUI
                SimulatorApplication.launch(args, networkFile);
            }


        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println("Unexpected exception:" + e.getMessage());
        }

    }

    private static void simulate(File networkFile, int destinationId, Protocol protocol) {

        int repetitionCount = 1000;
        int minDelay = 0;
        int maxDelay = 10;

        try {
            NetworkParser parser = new NetworkParser();
            parser.parse(networkFile);

            Engine engine = new Engine(new RandomScheduler(minDelay, maxDelay));
            Simulator simulator = SimulatorFactory.newSimulator(
                    engine, parser.getNetwork(), destinationId, protocol);

            String reportFileName = networkFile.getName().replaceFirst("\\.gv",
                    String.format("-dest%02d.csv", destinationId));
            File reportFile = new File(networkFile.getParent(), reportFileName);

            try (Reporter reporter = new CSVReporter(reportFile, parser.getNetwork())) {
                reporter.dumpBasicInfo(parser.getNetwork(), destinationId, minDelay, maxDelay, protocol, simulator);

                for (int i = 0; i < repetitionCount; i++) {
                    simulator.simulate();

                    try {
                        simulator.report(reporter);
                    } catch (IOException e) {
                        System.err.println("could not generate report");
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("can not open the file");
        } catch (TokenMgrError | ParseException | NodeExistsException | NodeNotFoundException | InvalidTagException e) {
            System.err.println("network file is corrupted");
        }

    }

}
