package policies.siblings;

/**
 * Represents a self attribute. It is implemented as a singleton.
 */
public class SelfAttribute extends SiblingAttribute {

    private static final SelfAttribute singleton = new SelfAttribute();

    private SelfAttribute() { // CAN NOT BE INSTANTIATED DIRECTLY
        super(0);
    }

    /**
     * Returns a self attribute instance.
     *
     * @return self attribute instance.
     */
    public static SelfAttribute self() {
        return singleton;
    }

    @Override
    protected Type getType() {
        return Type.SELF;
    }

    /**
     * Creates an instance of Customer Attribute.
     *
     * @param newHopCount hop count to initiate new sibling attribute with.
     * @return new instance of the same type.
     */
    @Override
    public SiblingAttribute newInstance(int newHopCount) {
        throw new UnsupportedOperationException("self attributes can not be instantiated");
    }

    @Override
    public String toString() {
        return "○";
    }

}
