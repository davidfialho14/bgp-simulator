package core.policies.peerplus;

/**
 * Represents a peer+ attribute. It is implemented as a singleton.
 */
public class PeerPlusAttribute extends PeerPlusAbstractAttribute {

    private static final PeerPlusAttribute singleton = new PeerPlusAttribute();

    private PeerPlusAttribute() {
    }  // CAN NOT BE INSTANTIATED DIRECTLY

    /**
     * Returns a peer+ attribute instance.
     *
     * @return peer+ attribute instance.
     */
    public static PeerPlusAttribute peerplus() {
        return singleton;
    }

    @Override
    Type getType() {
        return Type.PEER_PLUS;
    }

    @Override
    public String toString() {
        return "r+";
    }
}
