package main.cli;

import io.ParseException;
import io.networkreaders.exceptions.TopologyParseException;
import main.ErrorHandler;

import java.io.IOException;

/**
 * Implements the handling of errors by the CLI application.
 */
public class CLIErrorHandler implements ErrorHandler {

    @Override
    public void onTopologyLoadIOException(IOException exception) {
        System.err.println("can not open the topology file");
    }

    @Override
    public void onTopologyLoadParseException(TopologyParseException exception) {
        System.err.println("topology file is corrupted");
    }

    @Override
    public void onAnycastLoadIOException(IOException exception) {
        System.err.println("failed to open/read the anycast file: " + exception.getMessage());
    }

    @Override
    public void onAnycastLoadParseException(ParseException exception) {
        System.err.println("anycast file is corrupted: " + exception.getMessage());
    }

    @Override
    public void onReportingIOException(IOException exception) {
        System.err.println("failed to open/create/write report file");
    }

}
