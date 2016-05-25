package policies.shortestpath;

import io.InvalidTagException;
import network.Node;
import policies.Attribute;
import policies.Label;
import policies.Policy;

public class ShortestPathPolicy implements Policy {

    /**
     * Checks if a policy is a shortest path policy. This method does not require to create a new ShortestPathPolicy
     * instance to check if it is the desired policy.
     *
     * @return true if the policy is shortest path or false otherwise.
     */
    public static boolean isShortestPath(Policy policy) {
        return policy instanceof ShortestPathPolicy;
    }

    @Override
    public Attribute createSelf(Node node) {
        return new ShortestPathAttribute(0);
    }

    @Override
    public Label createLabel(String tag) throws InvalidTagException {
        try {
            return new ShortestPathLabel(Integer.parseInt(tag));
        } catch (NumberFormatException e) {
            // tag is not an integer
            throw new InvalidTagException(tag, "the ShortestPath policy's label tags must be integers");
        }
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ShortestPathPolicy;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
