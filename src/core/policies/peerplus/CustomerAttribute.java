package core.policies.peerplus;

/**
 * Represents a customer attribute. It is implemented as a singleton.
 */
public class CustomerAttribute extends PeerPlusAbstractAttribute {

    private static final CustomerAttribute singleton = new CustomerAttribute();

    private CustomerAttribute() {
    }  // CAN NOT BE INSTANTIATED DIRECTLY

    /**
     * Returns a customer attribute instance.
     *
     * @return customer attribute instance.
     */
    public static CustomerAttribute customer() {
        return singleton;
    }

    @Override
    Type getType() {
        return Type.CUSTOMER;
    }

    @Override
    public String toString() {
        return "c";
    }


}
