package protocols;

import network.Link;
import network.Node;
import policies.Attribute;
import policies.InvalidAttribute;
import core.Route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implements the reaction R1. R1 cuts the link completely not accepting more routes for that
 * destination from that link.
 */
class Reaction1 {

    protected Map<Node, Set<Link>> destinationCutLinks = new HashMap<>();

    public Attribute extend(Node destination, Link link, Attribute attribute) {
        Set<Link> cutLinks = destinationCutLinks.get(destination);
        if (cutLinks == null || !cutLinks.contains(link)) {
            return link.extend(attribute);
        } else {
            return InvalidAttribute.invalid();
        }
    }

    public void setParameters(Link link, Route learnedRoute) {
        // add the link to the list of cut links for this destination
        Set<Link> newLinkSet = new HashSet<>();
        Set<Link> curLinkSet = destinationCutLinks.putIfAbsent(learnedRoute.getDestination(), newLinkSet);

        if (curLinkSet == null) {
            // there was not already a set of cut links for this destination
            newLinkSet.add(link);
        } else {
            // there was already a set of cut links for this destination
            curLinkSet.add(link);
        }
    }

}
