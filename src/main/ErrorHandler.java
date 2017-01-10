package main;

import io.DestinationNotFoundException;
import io.ParseException;
import io.topologyreaders.exceptions.TopologyParseException;

import java.io.File;
import java.io.IOException;


/**
 * Error handlers are a clean way to define how errors are handled when it is not possible to detect the errors
 * ourselves. For instance, when we have a GUI we want to prompt a graphical alert message to the user when an error
 * occurs, however, the simulator launcher is the one who detects the errors. For this reason we set an error handler
 * for the simulator launcher where we define what to do in case of each error.
 */
public interface ErrorHandler {

    /**
     * Returns a default handler implementation that implements the default behaviour for all errors.
     * The default behaviour is to print the stack trace to the console.
     *
     * @return default error handler instance.
     */
    static ErrorHandler defaultHandler() {
        return new ErrorHandler() {
        };
    }

    /**
     * Invoked when the topology is being loaded and an IO exception is thrown.
     *
     * @param exception thrown IO exception.
     */
    default void onTopologyLoadIOException(IOException exception) {
        exception.printStackTrace();
    }

    /**
     * Invoked when the topology is being loaded and a parse exception is thrown.
     *
     * @param exception thrown parse exception.
     */
    default void onTopologyLoadParseException(TopologyParseException exception) {
        exception.printStackTrace();
    }

    /**
     * Invoked when the anycast file is being loaded and an IO exception is thrown.
     *
     * @param exception thrown IO exception.
     */
    default void onAnycastLoadIOException(IOException exception) {
        exception.printStackTrace();
    }

    /**
     * Invoked when generating a report and an IOException is thrown.
     *
     * @param exception thrown IO exception.
     */
    default void onReportingIOException(IOException exception) {
        exception.printStackTrace();
    }

    /**
     * Invoked when trying to get the destination router but it fails to find it.
     *
     * @param destinationID ID of the destination not found.
     */
    default void onUnknownDestination(int destinationID) {
        System.err.println("Unknown destination " + destinationID);
    }

    /**
     * Invoked the anycast parser fails to find the destination router.
     *
     * @param exception     thrown exception.
     * @param anycastFile   anycast file where there was the failure.
     */
    default void onDestinationNotFoundOnAnycastFile(DestinationNotFoundException exception,
                                                    File anycastFile) {
        System.err.println("Failed to find destination '" + exception.getDestinationIDs() +
                "' in anycast file" + anycastFile);
    }

    /**
     * Invoked when the seeds are being loaded and an IO exception is thrown.
     *
     * @param exception thrown IO exception.
     */
    default void onSeedsFileLoadIOException(IOException exception) {
        exception.printStackTrace();
    }

    /**
     * Invoked when the number of seeds does not match the number of repetitions.
     *
     * @param seedCount         number of seeds.
     * @param repetitionCount   number of repetitions.
     */
    default void onSeedsCountDoesNotMatchRepetitionCount(int seedCount, int repetitionCount) {}

    /**
     * Invoked when a ParseException is thrown when parsing the anycast file.
     *
     * @param exception thrown parse exception.
     */
    default void onAnycastParseException(ParseException exception) {
        System.out.println("Parse error: " + exception.getMessage());
    }
}
