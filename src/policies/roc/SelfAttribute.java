package policies.roc;

/**
 * Represents a self attribute. It is implemented as a singleton.
 */
public class SelfAttribute extends RoCAttribute {

    private static final SelfAttribute singleton = new SelfAttribute();

    private SelfAttribute() {
    }  // CAN NOT BE INSTANTIATED DIRECTLY

    /**
     * Returns a self attribute instance.
     *
     * @return self attribute instance.
     */
    public static SelfAttribute self() {
        return singleton;
    }

    @Override
    Type getType() {
        return Type.SELF;
    }

    @Override
    public String toString() {
        return "○";
    }

}
