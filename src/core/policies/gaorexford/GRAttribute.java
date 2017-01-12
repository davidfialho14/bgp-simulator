package core.policies.gaorexford;

import core.Attribute;

import static core.InvalidAttribute.invalidAttr;

/**
 * The GR attribute implements the attributes for the Gao-Rexford routing policy.
 * Each attribute is implemented as using a single instance.
 *
 * Implementation note: GRAttribute was suppose to be an enum, however, due to the type conflict between the
 * Comparable interface implemented by the enum type and the attribute interface.
 */
public class GRAttribute implements Attribute {

    /**
     * This dictates the possible values of each attribute. The order with which the values are defined
     * defines the comparison order of the attribute values. For instance, a peer attribute is greater than
     * a customer attribute, therefore, it it defined after customer.
     */
    enum Value {
        Self,
        Customer,
        Peer,
        Provider
    }

    final Value value;  // value assigned to the attribute

    // Attribute instances - For each attribute (self, customer, peer, provider) an unique instance is
    // created and assigned the respective value
    private static final Attribute SELF = new GRAttribute(Value.Self);
    private static final Attribute CUSTOMER = new GRAttribute(Value.Customer);
    private static final Attribute PEER = new GRAttribute(Value.Peer);
    private static final Attribute PROVIDER = new GRAttribute(Value.Provider);

    // use the factory methods to create each attribute instance
    private GRAttribute(Value value) {
        this.value = value;
    }

    /**
     * Returns THE self attribute instance.
     * @return the self attribute instance.
     */
    public static Attribute self() {
        return SELF;
    }

    /**
     * Returns THE customer attribute instance.
     * @return the customer attribute instance.
     */
    public static Attribute customer() {
        return CUSTOMER;
    }

    /**
     * Returns THE peer attribute instance.
     * @return the peer attribute instance.
     */
    public static Attribute peer() {
        return PEER;
    }

    /**
     * Returns THE provider attribute instance.
     * @return the provider attribute instance.
     */
    public static Attribute provider() {
        return PROVIDER;
    }

    /**
     * Compares the attributes according to the order defined in the Value enum type. It also supports
     * comparing to invalid attributes. If the given attribute is invalid then it returns an negative value
     * every time.
     *
     * @param attribute attribute to compare to.
     * @return negative value if attribute if preferable to the other, a positive value if it less preferable
     *         and zero if they are equal.
     */
    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

        GRAttribute other = (GRAttribute) attribute;
        return value.compareTo(other.value);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof GRAttribute)) return false;

        GRAttribute otherAttribute = (GRAttribute) other;
        return other != invalidAttr() && value.equals(otherAttribute.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
