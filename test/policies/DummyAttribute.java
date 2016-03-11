package policies;

import network.Attribute;

public class DummyAttribute implements Attribute {

    Integer value = null;

    /**
     * Creates dummy attribute with value 0.
     */
    public DummyAttribute() {
        value = 0;
    }

    /**
     * Creates dummy attribute with the given value.
     */
    public DummyAttribute(int value) {
        this.value = value;
    }

    /**
     * Creates invalid dummy attribute.
     * @return invalid dummy attribute.
     */
    public static DummyAttribute createInvalid() {
        DummyAttribute dummy = new DummyAttribute();
        dummy.value = null;
        return dummy;
    }

    @Override
    public boolean isInvalid() {
        return value == null;
    }

    /**
     * Dummy attributes are preferred (<) depending on its value. A higher value means the attribute has
     * higher preference. An invalid dummy has always the lowest preference.
     * @param attribute dummy attribute to be compared.
     * @return
     */
    @Override
    public int compareTo(Attribute attribute) {
        DummyAttribute other = (DummyAttribute) attribute;

        if (this.isInvalid() && other.isInvalid()) return 0;
        else if (this.isInvalid() && !other.isInvalid()) return 1;
        else if (!this.isInvalid() && other.isInvalid()) return -1;
        else return other.value - this.value;
    }

    @Override
    public String toString() {
        return "DummyAttribute(" + value + ')';
    }
}
