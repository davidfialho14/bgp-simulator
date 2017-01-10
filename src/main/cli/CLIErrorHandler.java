package main.cli;


import io.DestinationNotFoundException;
import io.topologyreaders.exceptions.TopologyParseException;
import main.ErrorHandler;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Implements the handling of errors by the CLI application.
 */
public class CLIErrorHandler implements ErrorHandler {

    @Override
    public void onTopologyLoadIOException(IOException exception) {
        System.err.println("Can not open the topology file: " + exception.getMessage());
    }

    @Override
    public void onTopologyLoadParseException(TopologyParseException exception) {
        System.err.println("Topology file is corrupted: " + exception.getMessage());
    }

    @Override
    public void onAnycastLoadIOException(IOException exception) {
        System.err.println("Failed to open/read the anycast file: " + exception.getMessage());
    }

    @Override
    public void onReportingIOException(IOException exception) {
        System.err.println("Failed to open/create/write report file: " + exception.getMessage());
    }

    @Override
    public void onUnknownDestination(int destinationID) {
        System.err.println("Failed to find the destination " + destinationID + " in the topology");
    }

    @Override
    public void onDestinationNotFoundOnAnycastFile(DestinationNotFoundException exception, File anycastFile) {

        String notFoundDestinations = exception.getDestinationIDs().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        System.err.println(String.format("Did not found destinations %s in anycast file '%s'",
                notFoundDestinations, anycastFile));
    }

    @Override
    public void onSeedsFileLoadIOException(IOException exception) {
        System.err.println("Can not open the seeds file: " + exception.getMessage());
    }

    @Override
    public void onSeedsCountDoesNotMatchRepetitionCount(int seedCount, int repetitionCount) {
        System.err.println(String.format("Number of seeds (%d) and number of " +
                "repetitions (%d) does not match", seedCount, repetitionCount));
    }

}
