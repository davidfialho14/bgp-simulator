package core.policies.peerplus;

/**
 * Represents a provider attribute. It is implemented as a singleton.
 */
public class ProviderAttribute extends PeerPlusAbstractAttribute {

    private static final ProviderAttribute singleton = new ProviderAttribute();

    private ProviderAttribute() {
    }  // CAN NOT BE INSTANTIATED DIRECTLY

    /**
     * Returns a provider attribute instance.
     *
     * @return provider attribute instance.
     */
    public static ProviderAttribute provider() {
        return singleton;
    }

    @Override
    Type getType() {
        return Type.PROVIDER;
    }

    @Override
    public String toString() {
        return "p";
    }
}
