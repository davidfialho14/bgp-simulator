package policies.gaorexford;

import io.InvalidTagException;
import network.Node;
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
