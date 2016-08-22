package core.network.exceptions;

/**
 * Thrown to indicate that a node already exists in the core.network.
 */
public class NodeExistsException extends Exception {

    public NodeExistsException(String s) {
        super(s);
    }

}
