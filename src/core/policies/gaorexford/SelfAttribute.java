package core.policies.gaorexford;

/**
 * Represents a self attribute. It is implemented as a singleton.
 */
public class SelfAttribute extends GaoRexfordAttribute {

    private static final SelfAttribute singleton = new SelfAttribute();

    /**
     * Returns a self attribute instance.
     * @return self attribute instance.
     */
    public static SelfAttribute self() {
        return singleton;
    }

    private SelfAttribute() {}  // CAN NOT BE INSTANTIATED DIRECTLY

    @Override
    Type getType() {
        return Type.SELF;
    }

    @Override
    public String toString() {
        return "○";
    }
    
}
