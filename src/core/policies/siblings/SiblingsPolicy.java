package core.policies.siblings;

import core.Attribute;
import core.Label;
import core.Policy;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import static core.policies.siblings.SiblingLabel.siblingLabel;
import static core.policies.siblings.SiblingsAttribute.self;
import static core.policies.siblings.SiblingsLabel.*;

public class SiblingsPolicy implements Policy {

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
            case "S":
                return siblingLabel();
            default:
                throw new InvalidPolicyTagException(tag, "not a valid tag for a Sibling label");
        }
    }

    /**
     * Returns a string with the name of the policy.
     *
     * @return string "Siblings"
     */
    @Override
    public String toString() {
        return "Siblings";
    }

}
