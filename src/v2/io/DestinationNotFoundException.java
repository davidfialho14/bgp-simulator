package v2.io;

/**
 * Thrown by the anycast file parser to indicate that it did not found the destination router requested in
 * the parse method.
 */
public class DestinationNotFoundException extends Exception {

    public DestinationNotFoundException(int destinationID) {
        super(String.format("did not found destination %d in the anycast file", destinationID));
    }
}
