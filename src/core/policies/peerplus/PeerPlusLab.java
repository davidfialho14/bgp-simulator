package core.policies.peerplus;

import core.Attribute;
import core.Label;
import core.Link;
import core.policies.peerplus.PeerPlusAttribute.*;

import java.util.EnumMap;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.peerplus.PeerPlusAttribute.*;

public enum PeerPlusLab implements Label {

    PeerPlus(peerplus(), peerplus(), peerplus(), invalidAttr(), invalidAttr()),
    Customer(customer(), customer(), customer(), invalidAttr(), invalidAttr()),
    Peer(peer(), peer(), peer(), invalidAttr(), invalidAttr()),
    Provider(provider(), provider(), provider(), provider(), provider());

    private final EnumMap<Value, Attribute> map = new EnumMap<>(Value.class);

    PeerPlusLab(Attribute selfMapValue, Attribute peerplusMapValue, Attribute customerMapValue,
                Attribute peerMapValue, Attribute providerValue) {

        map.put(Value.Self, selfMapValue);
        map.put(Value.Customer, customerMapValue);
        map.put(Value.Peer, peerMapValue);
        map.put(Value.Provider, providerValue);
        map.put(Value.Provider, providerValue);
    }

    public static Label peerplusLabel() {
        return PeerPlus;
    }

    public static Label customerLabel() {
        return Customer;
    }

    public static Label peerLabel() {
        return Peer;
    }

    public static Label providerLabel() {
        return Provider;
    }

    @Override
    public Attribute extend(Link link, Attribute attribute) {
        if (attribute == invalidAttr()) return invalidAttr();

        PeerPlusAttribute peerplusAttribute = (PeerPlusAttribute) attribute;
        return map.get(peerplusAttribute.value);
    }

}
