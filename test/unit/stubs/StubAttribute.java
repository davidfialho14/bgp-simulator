package stubs;

import core.Attribute;

import static core.InvalidAttribute.invalidAttr;

public class StubAttribute implements Attribute {

    Integer value = null;

    /**
     * Creates stub attribute with value 0.
     */
    public StubAttribute() {
        value = 0;
    }

    /**
     * Creates stub attribute with the given value.
     */
    public StubAttribute(int value) {
        this.value = value;
    }

    /**
     * Dummy attributes are preferred (<) depending on its value. A higher value means the attribute has
     * higher preference. An invalidAttr stub has always the lowest preference.
     * @param attribute stub attribute to be compared.
     * @return
     */
    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

        StubAttribute other = (StubAttribute) attribute;
        return other.value - this.value;
    }

    @Override
    public String toString() {
        return "StubAttribute(" + value + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StubAttribute that = (StubAttribute) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
