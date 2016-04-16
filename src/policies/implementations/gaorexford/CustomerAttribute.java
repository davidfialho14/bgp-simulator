package policies.implementations.gaorexford;

/**
 * Represents a customer attribute. It is implemented as a singleton.
 */
public class CustomerAttribute extends GaoRexfordAttribute {

    private static final CustomerAttribute singleton = new CustomerAttribute();

    /**
     * Returns a customer attribute instance.
     * @return customer attribute instance.
     */
    public static CustomerAttribute customer() {
        return singleton;
    }

    private CustomerAttribute() {}  // CAN NOT BE INSTANTIATED DIRECTLY

    @Override
    Type getType() {
        return Type.CUSTOMER;
    }

    @Override
    public String toString() {
        return "c";
    }


}
