package policies;

import network.Label;

public class DummyLabel implements Label {


    @Override
    public boolean equals(Object o) {
        return this == o || o instanceof DummyLabel;
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
