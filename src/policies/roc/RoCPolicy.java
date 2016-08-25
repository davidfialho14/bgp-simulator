package policies.roc;

import core.Attribute;
import core.topology.Label;
import core.topology.Node;
import core.topology.Policy;
import io.networkreaders.exceptions.InvalidPolicyTagException;

import static policies.roc.SelfAttribute.self;

public class RoCPolicy implements Policy {

    @Override
    public Attribute createSelf(Node node) {
        return self();
    }

    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        switch (tag) {
            case "R+":
                return new PeerPlusLabel();
            case "C":
                return new CustomerLabel();
            case "R":
                return new PeerLabel();
            case "P":
                return new ProviderLabel();
            default:
                throw new InvalidPolicyTagException(tag, "not a valid tag for a Gao Rexford label");
        }
    }

    /**
     * Returns a string with the name of the policy.
     *
     * @return string "RoC"
     */
    @Override
    public String toString() {
        return "RoC";
    }

}
