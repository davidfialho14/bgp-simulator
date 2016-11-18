package v2.core.policies.shortestpath;


import io.networkreaders.exceptions.InvalidPolicyTagException;
import v2.core.Attribute;
import v2.core.Label;
import v2.core.Policy;

public class ShortestPathPolicy implements Policy {

    @Override
    public Attribute createSelf() {
        return new ShortestPathAttribute(0);
    }

    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        try {
            return new ShortestPathLabel(Integer.parseInt(tag));
        } catch (NumberFormatException e) {
            // tag is not an integer
            throw new InvalidPolicyTagException(tag, "the ShortestPath policy's label tags must be integers");
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

    /**
     * Returns a string with the name of the policy.
     *
     * @return string "Shortest Path"
     */
    @Override
    public String toString() {
        return "Shortest Path";
    }

}
