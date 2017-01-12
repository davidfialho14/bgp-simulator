package core.policies.gaorexford;

import core.Attribute;
import core.Label;
import core.Policy;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import static core.policies.gaorexford.GRAttribute.self;
import static core.policies.gaorexford.GRLabel.customerLabel;
import static core.policies.gaorexford.GRLabel.peerLabel;
import static core.policies.gaorexford.GRLabel.providerLabel;

public class GaoRexfordPolicy implements Policy {

    @Override
    public Attribute createSelf() {
        return self();
    }

    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        switch (tag) {
            case "C":
                return customerLabel();
            case "R":
                return peerLabel();
            case "P":
                return providerLabel();
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
