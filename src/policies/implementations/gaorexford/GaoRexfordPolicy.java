package policies.implementations.gaorexford;

import io.InvalidTagException;
import network.Node;
import policies.Attribute;
import policies.Label;
import policies.Policy;

import static policies.implementations.gaorexford.SelfAttribute.self;

public class GaoRexfordPolicy implements Policy {

    @Override
    public Attribute createSelf(Node node) {
        return self();
    }

    @Override
    public Label createLabel(String tag) throws InvalidTagException {
        switch (tag) {
            case "C":
                return new CustomerLabel();
            case "R":
                return new PeerLabel();
            case "P":
                return new ProviderLabel();
            default:
                throw new InvalidTagException(tag, "not a valid tag for a Gao Rexford label");
        }
    }
}
