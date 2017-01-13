package core.policies.peerplus;

import core.Attribute;
import core.Label;
import core.Policy;
import io.topologyreaders.exceptions.InvalidPolicyTagException;

import static core.policies.peerplus.PeerPlusAttribute.self;
import static core.policies.peerplus.PeerPlusLabel.*;

public enum PeerPlusPolicy implements Policy {
    INSTANCE;

    public static PeerPlusPolicy peerplusPolicy() {
        return INSTANCE;
    }

    @Override
    public Attribute createSelf() {
        return self();
    }

    @Override
    public Label createLabel(String tag) throws InvalidPolicyTagException {
        switch (tag) {
            case "R+":
                return peerplusLabel();
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
     * @return string "Peer+"
     */
    @Override
    public String toString() {
        return "Peer+";
    }

}
