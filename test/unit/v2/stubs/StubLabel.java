package v2.stubs;


import v2.core.Attribute;
import v2.core.Label;
import v2.core.Link;

import static v2.stubs.Stubs.stubAttr;


public class StubLabel implements Label {

    StubLabel() {}  // Use the Factory methods in the Stubs Interface! - it is more readable

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof StubLabel;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    /**
     * Always returns a stub attribute with the its value incremented.
     *
     * @return a stub attribute with the its value incremented.
     */
    @Override
    public Attribute extend(Link link, Attribute attribute) {
        StubAttribute stubAttribute = (StubAttribute) attribute;
        return stubAttr(stubAttribute.value + 1);
    }

}
