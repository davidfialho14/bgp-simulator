package v2.core.exceptions;

/**
 * Thrown to indicate that a router does not exist in a certain topology.
 */
public class RouterNotFoundException extends Exception {

    public RouterNotFoundException(String s) {
        super(s);
    }
}
