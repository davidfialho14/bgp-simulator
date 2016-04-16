package policies;

/**
 * Implements an invalid attribute. This attribute can be used with any class of attributes to represent invalid
 * attributes.
 */
public class InvalidAttribute extends Attribute {

    /*
        It exists only one unique Invalid Attribute instance that can be accessed through the
        static method invalid().
     */
    private static final InvalidAttribute invalid = new InvalidAttribute();

    /**
     * Returns always the same instance of an invalid attribute. Its the only way to get an invalid attribute
     * instance.
     * @return invalid attribute instance.
     */
    public static InvalidAttribute invalid() {
        return invalid;
    }

    private InvalidAttribute() {}   // not be instantiated directly

    /**
     * Returns always true.
     * @return true.
     */
    @Override
    public boolean isInvalid() {
        return true;
    }

    /**
     * The invalid attribute is equal to other invalid attributes and greater than any other attribute.
     * @param attribute attribute to be compared.
     * @return 0 if attribute is invalid or greater than 0 if is not an invalid attribute.
     */
    @Override
    public int compareTo(Attribute attribute) {
        if (attribute instanceof InvalidAttribute) {
            return 0;
        } else {
            return 1;
        }
    }
}
