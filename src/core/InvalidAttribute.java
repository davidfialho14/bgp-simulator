package core;


/**
 * Represents an invalid attribute. This attribute can be used with any policy implementation and must always be the
 * least preferable attribute. It is implemented as a singleton meaning it has only one instance every time.
 *
 * Invalid attribute can not be implemented as an enum type because there is a conflict between the enum
 * comparable interface and the comparable interface of an attribute.
 */
public class InvalidAttribute implements Attribute {

    // It exists only one unique Invalid Attribute instance that can be accessed through the
    // static method invalidAttr().
    private static final InvalidAttribute INSTANCE = new InvalidAttribute();

    private InvalidAttribute() {
    } // not be instantiated directly

    /**
     * Returns always the same instance of an invalid attribute. Its the only way to get an invalid attribute
     * instance.
     *
     * @return invalid attribute instance.
     */
    public static InvalidAttribute invalidAttr() {
        return INSTANCE;
    }

    /**
     * The invalid attribute is equal to other invalid attributes and greater than any other attribute.
     *
     * @param attribute attribute to be compared.
     * @return 0 if attribute is invalid or greater than 0 if is not an invalid attribute.
     */
    @Override
    public int compareTo(Attribute attribute) {
        return attribute == invalidAttr() ? 0 : 1;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object other) {
        return other == invalidAttr();
    }

    // HASHCODE - Since there is only one object the default hashCode() implementation is sufficient

    @Override
    public String toString() {
        return "•";
    }
}
