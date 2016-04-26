package simulation.implementations.linkbreakers;

/**
 * Thrown to indicate that a link does not exist in a given network.
 */
public class LinkNotFoundException extends Exception {

    public LinkNotFoundException(String s) {
        super(s);
    }
}
