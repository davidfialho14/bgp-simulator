package main;

import core.Engine;
import core.Protocol;
import core.schedulers.RandomScheduler;
import core.topology.Topology;
import io.networkreaders.GraphvizReader;
import io.networkreaders.PolicyTagger;
import io.networkreaders.TopologyReader;
import io.networkreaders.exceptions.ParseException;
import io.reporters.CSVReporter;
import io.reporters.Reporter;
import main.gui.SimulatorApplication;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import policies.gaorexford.GaoRexfordPolicy;
import policies.roc.RoCPolicy;
import policies.shortestpath.ShortestPathPolicy;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import simulators.Simulator;
import simulators.SimulatorFactory;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        PolicyTagger.register(new ShortestPathPolicy(), "ShortestPath");
        PolicyTagger.register(new RoCPolicy(), "RoC");
        PolicyTagger.register(new GaoRexfordPolicy(), "GaoRexford");

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("d", "destination", true, "simulate with the given destination id");
        options.addOption("n", "topology", true, "core.topology to be simulated");
        options.addOption("c", "repetition_count", true, "number of repetitions");
        options.addOption("d2", "use detection 2 instead of detection 1");
        options.addOption("deploy", true, "time deploy detection");

        // parse the command line arguments
        try {
            CommandLine cmd = parser.parse(options, args);

            File networkFile = null;
            if (cmd.hasOption("topology")) {
                networkFile = new File(cmd.getOptionValue("topology"));

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
                    System.err.println("It is missing the core.topology file");
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

        // read the topology and handle possible errors
        Topology topology = null;   // stores the read topology
        try (TopologyReader topologyReader = new GraphvizReader(networkFile)) {
            topology = topologyReader.read();

        } catch (IOException e) {
            System.err.println("can not open the file");
        } catch (ParseException e) {
            System.err.println("topology file is corrupted");
        }

        if (topology != null) {
            Engine engine = new Engine(new RandomScheduler(minDelay, maxDelay));

            Simulator simulator;
            if (deployTime == null) {
                simulator = SimulatorFactory.newSimulator(
                        engine, topology, destinationId, protocol);
            } else {
                simulator = SimulatorFactory.newSimulator(
                        engine, topology, destinationId, protocol, deployTime);
            }

            String reportFileName = networkFile.getName().replaceFirst("\\.gv",
                    String.format("-dest%02d.csv", destinationId));
            File reportFile = new File(networkFile.getParent(), reportFileName);

            try (Reporter reporter = new CSVReporter(reportFile, topology)) {
                reporter.dumpBasicInfo(topology, destinationId, minDelay, maxDelay, protocol, simulator);

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
            } catch (IOException e) {
                System.err.println("failed to open/create/write report file");
            }
        }
    }

}
