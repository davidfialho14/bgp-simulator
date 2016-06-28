package policies.siblings;

import io.InvalidTagException;
import network.Node;
import policies.Attribute;
import policies.Label;
import policies.Policy;

import static policies.siblings.SelfAttribute.self;

public class SiblingsPolicy implements Policy {

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
            case "S":
                return new SiblingLabel();
            default:
                throw new InvalidTagException(tag, "not a valid tag for a Sibling label");
        }
    }
}
