package core.topology.exceptions;

/**
 * Thrown to indicate that a node does not exist in a given topology.
 */
public class NodeNotFoundException extends Exception {

    public NodeNotFoundException(String s) {
        super(s);
    }
}
