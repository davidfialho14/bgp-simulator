package v2.core.policies.peerplus;

/**
 * Represents a peer attribute. It is implemented as a singleton.
 */
public class PeerAttribute extends PeerPlusAbstractAttribute {

    private static final PeerAttribute singleton = new PeerAttribute();

    private PeerAttribute() {
    }  // CAN NOT BE INSTANTIATED DIRECTLY

    /**
     * Returns a peer attribute instance.
     *
     * @return peer attribute instance.
     */
    public static PeerAttribute peer() {
        return singleton;
    }

    @Override
    Type getType() {
        return Type.PEER;
    }

    @Override
    public String toString() {
        return "r";
    }
}
