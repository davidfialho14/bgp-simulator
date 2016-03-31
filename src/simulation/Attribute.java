package simulation;

public interface Attribute extends Comparable<Attribute> {

    /**
     * Checks if the attribute is invalid.
     * @return true if the attribute is invalid and false otherwise.
     */
    boolean isInvalid();

    /**
     * Creates an invalid attribute of the same type.
     * @return invalid attribute of the same type.
     */
    Attribute createInvalid();

}
