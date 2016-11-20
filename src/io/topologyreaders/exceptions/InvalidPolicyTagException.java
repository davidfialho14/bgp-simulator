package io.topologyreaders.exceptions;

/**
 * Thrown when trying to parse an invalid policy tag. Stores a detailed message of the error and the
 * invalid tag.
 */
public class InvalidPolicyTagException extends TopologyParseException {

    private String tag;

    public InvalidPolicyTagException(String tag, String message) {
        super(message);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
