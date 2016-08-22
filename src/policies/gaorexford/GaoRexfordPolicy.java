package policies.gaorexford;

import io.networkreaders.exceptions.InvalidPolicyTagException;
import core.network.Node;
import core.Attribute;
import core.Label;
import core.Policy;

import static policies.gaorexford.SelfAttribute.self;

public class GaoRexfordPolicy implements Policy {

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
            default:
                throw new InvalidPolicyTagException(tag, "not a valid tag for a Gao Rexford label");
        }
    }
}
