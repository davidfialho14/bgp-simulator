package main;

import core.Protocol;
import io.networkreaders.GraphvizReaderFactory;
import io.networkreaders.PolicyTagger;
import io.networkreaders.exceptions.ParseException;
import io.reporters.CSVReporterFactory;
import javafx.scene.control.Alert;
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
        options.addOption("n", "initialTopology", true, "core.initialTopology to be simulated");
        options.addOption("c", "repetition_count", true, "number of repetitions");
        options.addOption("d2", "use detection 2 instead of detection 1");
        options.addOption("deploy", true, "time deploy detection");

        // parse the command line arguments
        try {
            CommandLine cmd = parser.parse(options, args);

            File networkFile = null;
            if (cmd.hasOption("initialTopology")) {
                networkFile = new File(cmd.getOptionValue("initialTopology"));

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
                    System.err.println("It is missing the core.initialTopology file");
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

    private static void simulate(File topologyFile, int destinationId, int repetitionCount, Protocol protocol,
                                 Integer deployTime) {
        int minDelay = 0;
        int maxDelay = 10;

        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public void onTopologyLoadIOException(IOException exception) {
                System.err.println("can not open the file");
            }

            @Override
            public void onTopologyLoadParseException(ParseException exception) {
                System.err.println("initialTopology file is corrupted");
            }

            @Override
            public void onReportingIOException(IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "failed to open/create/write report file");
                alert.setHeaderText("IO Error");
                alert.showAndWait();
            }
        };

        SimulatorLauncher simulatorLauncher = new SimulatorLauncher.Builder(
                errorHandler,
                new GraphvizReaderFactory(),
                minDelay, maxDelay,
                destinationId,
                repetitionCount,
                protocol,
                new CSVReporterFactory())
                .fullDeployment(deployTime != null,
                        deployTime == null ? 0 : deployTime)
                .build();

        String reportFileName = topologyFile.getName().replaceFirst("\\.gv",
                String.format("-dest%02d.csv", destinationId));
        File reportFile = new File(topologyFile.getParent(), reportFileName);

        simulatorLauncher.launch(topologyFile, reportFile);

    }

}
