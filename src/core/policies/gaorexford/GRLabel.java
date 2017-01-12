package core.policies.gaorexford;

import core.Attribute;
import core.Label;
import core.Link;
import core.policies.gaorexford.GRAttribute.Value;

import java.util.EnumMap;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.GRAttribute.customer;
import static core.policies.gaorexford.GRAttribute.peer;
import static core.policies.gaorexford.GRAttribute.provider;

public enum GRLabel implements Label {

    Customer(customer(), customer(), invalidAttr(), invalidAttr()),
    Peer(peer(), peer(), invalidAttr(), invalidAttr()),
    Provider(provider(), provider(), provider(), provider());

    private final EnumMap<GRAttribute.Value, Attribute> map = new EnumMap<>(GRAttribute.Value.class);

    GRLabel(Attribute selfMapValue, Attribute customerMapValue, Attribute peerMapValue,
            Attribute providerValue) {

        map.put(Value.Self, selfMapValue);
        map.put(Value.Customer, customerMapValue);
        map.put(Value.Peer, peerMapValue);
        map.put(Value.Provider, providerValue);
        map.put(Value.Provider, providerValue);
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

        GRAttribute gaoRexfordAttribute = (GRAttribute) attribute;
        return map.getOrDefault(gaoRexfordAttribute.value, invalidAttr());
    }

}
