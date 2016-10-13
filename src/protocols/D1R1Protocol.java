package protocols;

/**
 * Its just a wrapper around a generic protocol implementing the Simple Detection and the Cut-Off Reaction.
 */
public class D1R1Protocol extends GenericProtocol {

    /**
     * Initializes the protocol given the simple detection and cut-off reaction implementations.
     */
    public D1R1Protocol() {
        super(new SimpleDetection(), new CutOffReaction());
    }

}
