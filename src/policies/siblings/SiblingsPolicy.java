package policies.siblings;

import core.Attribute;
import core.topology.Label;
import core.topology.Node;
import core.topology.Policy;
import io.networkreaders.exceptions.InvalidPolicyTagException;

import static policies.siblings.SelfAttribute.self;

public class SiblingsPolicy implements Policy {

    @Override
    public Attribute createSelf(Node node) {
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
}
