package policies.gaorexford;

/**
 * Represents a provider attribute. It is implemented as a singleton.
 */
public class ProviderAttribute extends GaoRexfordAttribute {

    private static final ProviderAttribute singleton = new ProviderAttribute();

    /**
     * Returns a provider attribute instance.
     * @return provider attribute instance.
     */
    public static ProviderAttribute provider() {
        return singleton;
    }

    private ProviderAttribute() {}  // CAN NOT BE INSTANTIATED DIRECTLY

    @Override
    Type getType() {
        return Type.PROVIDER;
    }

    @Override
    public String toString() {
        return "p";
    }
}
