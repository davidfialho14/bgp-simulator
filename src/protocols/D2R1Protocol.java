package protocols;

/**
 * Its just a wrapper around a generic protocol implementing the Path Detection and the Cut-Off Reaction.
 */
public class D2R1Protocol extends GenericProtocol {

    /**
     * Initializes the protocol given the path detection and cut-off reaction implementations.
     */
    public D2R1Protocol() {
        super(new PathDetection(), new CutOffReaction());
    }

}
