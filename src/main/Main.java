package main;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.TokenMgrError;
import gui.SimulatorApplication;
import io.InvalidTagException;
import io.NetworkParser;
import io.reporters.CSVReporter;
import io.reporters.Reporter;
import core.network.exceptions.NodeExistsException;
import core.network.exceptions.NodeNotFoundException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import core.Protocol;
import core.Engine;
import core.schedulers.RandomScheduler;
import simulators.Simulator;
import simulators.SimulatorFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("d", "destination", true, "simulate with the given destination id");
        options.addOption("n", "network", true, "core.network to be simulated");
        options.addOption("c", "repetition_count", true, "number of repetitions");
        options.addOption("d2", "use detection 2 instead of detection 1");
        options.addOption("deploy", true, "time deploy detection");

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
                int repetitionCount = Integer.parseInt(cmd.getOptionValue("repetition_count"));

                if (networkFile == null) {
                    System.err.println("It is missing the core.network file");
                    System.exit(1);
                }

                Protocol protocol;
                if (cmd.hasOption("d2")) {
                    protocol = new D2R1Protocol();
                } else {
                    protocol = new D1R1Protocol();
                }

                Integer deployTime = null;
                if (cmd.hasOption("deploy")) {
                    deployTime = Integer.parseInt(cmd.getOptionValue("deploy"));
                }

                simulate(networkFile, destinationId, repetitionCount, protocol, deployTime);

            } else {
                // display the GUI
                SimulatorApplication.launch(args, networkFile);
            }


        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println("Unexpected exception:" + e.getMessage());
        }

    }

    private static void simulate(File networkFile, int destinationId, int repetitionCount, Protocol protocol,
                                 Integer deployTime) {
        int minDelay = 0;
        int maxDelay = 10;

        try {
            NetworkParser parser = new NetworkParser();
            parser.parse(networkFile);

            Engine engine = new Engine(new RandomScheduler(minDelay, maxDelay));

            Simulator simulator;
            if (deployTime == null) {
                simulator = SimulatorFactory.newSimulator(
                        engine, parser.getNetwork(), destinationId, protocol);
            } else {
                simulator = SimulatorFactory.newSimulator(
                        engine, parser.getNetwork(), destinationId, protocol, deployTime);
            }

            String reportFileName = networkFile.getName().replaceFirst("\\.gv",
                    String.format("-dest%02d.csv", destinationId));
            File reportFile = new File(networkFile.getParent(), reportFileName);

            try (Reporter reporter = new CSVReporter(reportFile, parser.getNetwork())) {
                reporter.dumpBasicInfo(parser.getNetwork(), destinationId, minDelay, maxDelay, protocol, simulator);

                for (int i = 0; i < repetitionCount; i++) {
                    long startTime = System.currentTimeMillis();
                    System.out.println("Started core " + i);

                    simulator.simulate();

                    try {
                        simulator.report(reporter);
                    } catch (IOException e) {
                        System.err.println("could not generate report");
                    }

                    long estimatedTime = System.currentTimeMillis() - startTime;
                    System.out.println(String.format("Finished core " + i + " in %.2f", estimatedTime / 1000.0));
                }
            }

        } catch (IOException e) {
            System.err.println("can not open the file");
        } catch (TokenMgrError | ParseException | NodeExistsException | NodeNotFoundException | InvalidTagException e) {
            System.err.println("core.network file is corrupted");
        }

    }

}
