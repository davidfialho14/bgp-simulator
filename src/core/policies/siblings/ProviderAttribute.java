package core.policies.siblings;

/**
 * Represents a provider attribute.
 */
public class ProviderAttribute extends SiblingAttribute {

    private ProviderAttribute(int hopCount) { // CAN NOT BE INSTANTIATED DIRECTLY
        super(hopCount);
    }

    /**
     * Returns a provider attribute instance.
     *
     * @return provider attribute instance.
     */
    public static ProviderAttribute provider(int hopCount) {
        return new ProviderAttribute(hopCount);
    }

    @Override
    protected Type getType() {
        return Type.PROVIDER;
    }

    /**
     * Creates an instance of Provider Attribute.
     *
     * @param newHopCount hop count to initiate new sibling attribute with.
     * @return new instance of the same type.
     */
    @Override
    public SiblingAttribute newInstance(int newHopCount) {
        return new ProviderAttribute(newHopCount);
    }

    @Override
    public String toString() {
        return String.format("(p, %d)", hopCount);
    }
}
