package simulation.networks.shortestpath;

import policies.implementations.shortestpath.ShortestPathAttribute;
import policies.implementations.shortestpath.ShortestPathLabel;
import simulation.networks.Topology;

public abstract class ShortestPathTopology extends Topology {

    // ----- WRAPPER FUNCTIONS ---------------------------------------------------------------------------------------

    /**
     * Wrapper around calling the ShortestPathLabel constructor. This makes it easier to read when setting up
     * each route in the route tables.
     * @param length length to associate with the label.
     * @return new ShortestPathLabel instance with the given length.
     */
    protected static ShortestPathLabel label(int length) {
        return new ShortestPathLabel(length);
    }

    /**
     * Wrapper around calling the ShortestPathAttribute constructor. This makes it easier to read when setting up
     * each route in the route tables.
     * @param length length to associate with the attribute.
     * @return new ShortestPathAttribute instance with the given length.
     */
    protected static ShortestPathAttribute attribute(int length) {
        return new ShortestPathAttribute(length);
    }

}
