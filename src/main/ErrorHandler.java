package main;

import io.ParseException;
import io.networkreaders.exceptions.TopologyParseException;

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
     * Invoked when the anycast file is being loaded and a parse exception is thrown.
     *
     * @param exception thrown parse exception.
     */
    default void onAnycastLoadParseException(ParseException exception) {
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
}
