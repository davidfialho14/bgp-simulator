package network;

public interface Attribute extends Comparable<Attribute> {

    /**
     * Checks if the attribute is invalid.
     * @return true if the attribute is invalid and false otherwise.
     */
    boolean isInvalid();

}
