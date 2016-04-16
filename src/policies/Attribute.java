package policies;

public abstract class Attribute implements Comparable<Attribute> {

    /**
     * Checks if the attribute is invalid.
     * @return true if the attribute is invalid and false otherwise.
     */
    public boolean isInvalid() { return false; }

    // TODO remove the createInvalid() attribute method!

    /**
     * Creates an invalid attribute of the same type.
     * @return invalid attribute of the same type.
     */
    abstract public Attribute createInvalid();

}
