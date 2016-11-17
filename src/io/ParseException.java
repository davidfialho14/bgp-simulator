package io;

/**
 * Generic parse exception thrown when a parse error occurs while parsing a formatted file.
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

    /**
     * Returns the line number of the error.
     *
     * @return the line number of the error
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return super.getMessage().concat(" (in line " + lineNumber + ")");
    }
}
