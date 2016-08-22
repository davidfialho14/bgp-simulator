package io.networkreaders.exceptions;

import java.io.IOException;

/**
 * Thrown when a parse error occurs while parsing a file.
 */
public class ParseException extends Exception {

    /**
     * Constructs a new parse exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParseException(String message) throws IOException, ParseException {
        super(message);
    }
}
