package stubs;


import core.Attribute;

import static core.InvalidAttribute.invalidAttr;


public class StubAttribute implements Attribute {

    Integer value = null;

    /**
     * Creates stub attribute with the given value. Use the Factory methods in the Stubs Interface!
     */
    StubAttribute(int value) {
        this.value = value;
    }

    /**
     * Dummy attributes are preferred (<) depending on its value. A higher value means the attribute has
     * higher preference. An invalid stub has always the lowest preference.
     * @param attribute stub attribute to be compared.
     */
    @Override
    public int compareTo(Attribute attribute) {
        if (attribute == invalidAttr()) return -1;

        StubAttribute other = (StubAttribute) attribute;
        return other.value - this.value;
    }

    @Override
    public String toString() {
        return "Stub(" + value + ')';
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
