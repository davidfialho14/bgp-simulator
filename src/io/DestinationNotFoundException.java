package io;

import java.util.Collection;
import java.util.Collections;

/**
 * Thrown by the anycast file parser to indicate that it did not found the destination router requested in
 * the parse method.
 */
public class DestinationNotFoundException extends Exception {

    private final Collection<Integer> destinationIDs;

    public DestinationNotFoundException(int destinationID) {
        super(String.format("did not found destination %d in the anycast file", destinationID));
        destinationIDs = Collections.singletonList(destinationID);
    }

    public DestinationNotFoundException(Collection<Integer> destinationIDs) {
        super("did not found some destinations in the anycast file");
        this.destinationIDs = destinationIDs;
    }

    public Collection<Integer> getDestinationIDs() {
        return destinationIDs;
    }

}
