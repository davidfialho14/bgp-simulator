package stubs;

import core.Attribute;
import core.topology.Label;
import core.topology.Link;

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
