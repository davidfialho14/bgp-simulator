package stubs;

import core.network.Link;
import core.Attribute;
import core.Label;

public class StubLabel implements Label {

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof StubLabel;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        throw new UnsupportedOperationException();
    }
}
