package io.topologyreaders.exceptions;


import io.ParseException;

/**
 * Thrown when a parse error occurs while parsing a file.
 */
public class TopologyParseException extends ParseException {

    /**
     * Constructs a new parse exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public TopologyParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new parse exception with the specified detail message and line number
     * specifying the line where the error occurred.
     *
     * @param message    the detail message. The detail message is saved for
     *                   later retrieval by the {@link #getMessage()} method.
     * @param lineNumber number of the line containing the parse error.
     */
    public TopologyParseException(String message, int lineNumber) {
        super(message, lineNumber);
    }
}
