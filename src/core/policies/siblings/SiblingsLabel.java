package core.policies.siblings;

import core.Attribute;
import core.Label;
import core.Link;

import java.util.HashMap;
import java.util.Map;

import static core.InvalidAttribute.invalidAttr;
import static core.policies.gaorexford.GRAttribute.customer;
import static core.policies.gaorexford.GRAttribute.peer;
import static core.policies.gaorexford.GRAttribute.provider;
import static core.policies.gaorexford.GRAttribute.*;
import static core.policies.siblings.SiblingsAttribute.customer;
import static core.policies.siblings.SiblingsAttribute.peer;
import static core.policies.siblings.SiblingsAttribute.provider;

public enum SiblingsLabel implements Label {

    Customer(customer(0), customer(0), invalidAttr(), invalidAttr()),
    Peer(peer(0), peer(0), invalidAttr(), invalidAttr()),
    Provider(provider(0), provider(0), provider(0), provider(0));

    private final Map<Attribute, Attribute> map = new HashMap<>();

    SiblingsLabel(Attribute selfMapValue, Attribute customerMapValue, Attribute peerMapValue,
                  Attribute providerValue) {

        map.put(self(), selfMapValue);
        map.put(customer(), customerMapValue);
        map.put(peer(), peerMapValue);
        map.put(provider(), providerValue);
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

        SiblingsAttribute siblingsAttribute = (SiblingsAttribute) attribute;
        return map.getOrDefault(siblingsAttribute.getBaseAttribute(), invalidAttr());
    }

}
