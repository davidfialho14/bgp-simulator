package policies;

import simulation.Attribute;
import simulation.Label;
import network.Link;

public class DummyLabel implements Label {

    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof DummyLabel;
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
