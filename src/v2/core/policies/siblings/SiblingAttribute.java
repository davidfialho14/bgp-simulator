package v2.core.policies.siblings;


import v2.core.Attribute;

import static v2.core.InvalidAttribute.invalidAttr;

public abstract class SiblingAttribute implements Attribute {

    protected int hopCount;   // number of sibling hops

    protected SiblingAttribute(int hopCount) {
        this.hopCount = hopCount;
    }

    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

        SiblingAttribute other = (SiblingAttribute) attribute;

        int comparison = this.getType().compareTo(other.getType());
        if (comparison == 0) {
            comparison = this.hopCount - other.hopCount;
        }

        return comparison;
    }

    protected abstract Type getType();

    public int getHopCount() {
        return hopCount;
    }

    /**
     * Creates an instance of the respective sibling attribute with the new hop count.
     *
     * @param newHopCount hop count to initiate new sibling attribute with.
     * @return new instance of the same type.
     */
    public abstract SiblingAttribute newInstance(int newHopCount);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiblingAttribute that = (SiblingAttribute) o;

        return hopCount == that.hopCount && getType() == that.getType();

    }

    @Override
    public int hashCode() {
        int result = hopCount;
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    /**
     * This enum models the type of the attributes.
     */
    protected enum Type {
        SELF, CUSTOMER, PEER, PROVIDER
    }
}
