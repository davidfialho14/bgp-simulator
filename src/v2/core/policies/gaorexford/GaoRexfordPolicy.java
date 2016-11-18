package v2.core.policies.gaorexford;

import io.networkreaders.exceptions.InvalidPolicyTagException;
import v2.core.Attribute;
import v2.core.Label;
import v2.core.Policy;

import static v2.core.policies.gaorexford.SelfAttribute.self;

public class GaoRexfordPolicy implements Policy {

    @Override
    public Attribute createSelf() {
        return self();
    }

    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        switch (tag) {
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
     * @return string "Gao-Rexford"
     */
    @Override
    public String toString() {
        return "Gao-Rexford";
    }

}
