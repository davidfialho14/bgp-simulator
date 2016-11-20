package main.cli;


import io.DestinationNotFoundException;
import io.topologyreaders.exceptions.TopologyParseException;
import main.ErrorHandler;

import java.io.File;
import java.io.IOException;

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
    public void onDestinationNotFoundOnAnycastFile(DestinationNotFoundException exception, File anycastFile,
                                                   int destinationID) {
        System.err.println(String.format("Did not found destination %d in anycast file '%s'",
                destinationID, anycastFile));
    }

}
