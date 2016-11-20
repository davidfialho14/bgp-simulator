package core.policies.siblings;

/**
 * Represents a peer attribute.
 */
public class PeerAttribute extends SiblingAttribute {

    private PeerAttribute(int hopCount) { // CAN NOT BE INSTANTIATED DIRECTLY
        super(hopCount);
    }

    /**
     * Returns a peer attribute instance.
     *
     * @return peer attribute instance.
     */
    public static PeerAttribute peer(int hopCount) {
        return new PeerAttribute(hopCount);
    }

    @Override
    protected Type getType() {
        return Type.PEER;
    }

    /**
     * Creates an instance of Peer Attribute.
     *
     * @param newHopCount hop count to initiate new sibling attribute with.
     * @return new instance of the same type.
     */
    @Override
    public SiblingAttribute newInstance(int newHopCount) {
        return new PeerAttribute(newHopCount);
    }

    @Override
    public String toString() {
        return String.format("(r, %d)", hopCount);
    }

}
