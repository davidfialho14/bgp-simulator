package v2.core.policies.siblings;

import io.networkreaders.exceptions.InvalidPolicyTagException;
import v2.core.Attribute;
import v2.core.Label;
import v2.core.Policy;

import static v2.core.policies.siblings.SelfAttribute.self;

public class SiblingsPolicy implements Policy {

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
            case "S":
                return new SiblingLabel();
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
