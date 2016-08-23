package main;

import io.networkreaders.exceptions.ParseException;

import java.io.IOException;

public interface ErrorHandler {

    /**
     * Invoked when the topology is being loaded and an IO exception is thrown.
     *
     * @param exception thrown IO exception.
     */
    void onTopologyLoadIOException(IOException exception);

    /**
     * Invoked when the topology is being loaded and a parse exception is thrown.
     *
     * @param exception thrown parse exception.
     */
    void onTopologyLoadParseException(ParseException exception);

    /**
     * Invoked when generating a report and an IOException is thrown.
     *
     * @param exception thrown IO exception.
     */
    void onReportingIOException(IOException exception);
}
