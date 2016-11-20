package core.policies.siblings;

/**
 * Represents a customer attribute.
 */
public class CustomerAttribute extends SiblingAttribute {

    private CustomerAttribute(int hopCount) { // CAN NOT BE INSTANTIATED DIRECTLY
        super(hopCount);
    }

    /**
     * Returns a customer attribute instance.
     *
     * @return customer attribute instance.
     */
    public static CustomerAttribute customer(int hopCount) {
        return new CustomerAttribute(hopCount);
    }

    @Override
    protected Type getType() {
        return Type.CUSTOMER;
    }

    /**
     * Creates an instance of Customer Attribute.
     *
     * @param newHopCount hop count to initiate new sibling attribute with.
     * @return new instance of the same type.
     */
    @Override
    public SiblingAttribute newInstance(int newHopCount) {
        return new CustomerAttribute(newHopCount);
    }

    @Override
    public String toString() {
        return String.format("(c, %d)", hopCount);
    }

}
