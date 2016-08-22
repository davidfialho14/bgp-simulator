package io.networkreaders.exceptions;

/**
 * Thrown when a parse error occurs while parsing a file.
 */
public class ParseException extends Exception {

    private final int lineNumber;    // contains the number of the line with the error

    /**
     * Constructs a new parse exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParseException(String message) {
        super(message);
        lineNumber = 1; // by default the error is in the first line
    }

    /**
     * Constructs a new parse exception with the specified detail message and line number
     * specifying the line where the error occurred.
     *
     * @param message    the detail message. The detail message is saved for
     *                   later retrieval by the {@link #getMessage()} method.
     * @param lineNumber number of the line containing the parse error.
     */
    public ParseException(String message, int lineNumber) {
        super(message);
        this.lineNumber = lineNumber;
    }
}
