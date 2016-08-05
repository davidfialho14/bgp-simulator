package core;

public abstract class Attribute implements Comparable<Attribute> {

    /**
     * Checks if the attribute is invalid.
     * @return true if the attribute is invalid and false otherwise.
     */
    public boolean isInvalid() { return false; }

}
