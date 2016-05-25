package network.exceptions;

/**
 * Thrown to indicate that a node does not exist in a given network.
 */
public class NodeNotFoundException extends Exception {

    public NodeNotFoundException(String s) {
        super(s);
    }
}
