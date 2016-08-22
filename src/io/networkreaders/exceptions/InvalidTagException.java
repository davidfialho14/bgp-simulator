package io.networkreaders.exceptions;

public class InvalidTagException extends Exception {

    private String tag;

    public InvalidTagException(String tag, String message) {
        super(message);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
