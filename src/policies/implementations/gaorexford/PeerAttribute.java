package policies.implementations.gaorexford;

/**
 * Represents a peer attribute. It is implemented as a singleton.
 */
public class PeerAttribute extends GaoRexfordAttribute {

    private static final PeerAttribute singleton = new PeerAttribute();

    /**
     * Returns a peer attribute instance.
     * @return peer attribute instance.
     */
    public static PeerAttribute peer() {
        return singleton;
    }

    private PeerAttribute() {}  // CAN NOT BE INSTANTIATED DIRECTLY

    @Override
    Type getType() {
        return Type.PEER;
    }

    @Override
    public String toString() {
        return "r";
    }
}
