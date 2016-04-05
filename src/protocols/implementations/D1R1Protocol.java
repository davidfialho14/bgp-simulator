package protocols.implementations;

import network.Link;
import network.Node;
import policies.Attribute;
import protocols.Protocol;
import simulation.PathAttribute;
import simulation.Route;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implements the detection D1 and the reaction R1. D1 considers an oscillation when the learned route is preferred
 * to the elected route. R1 cuts the link completely not accepting more routes for that destination from that link.
 */
public class D1R1Protocol implements Protocol {

    Map<Node, Set<Link>> destinationCutLinks = new HashMap<>();

    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
        Set<Link> cutLinks = destinationCutLinks.get(destination);
        if (cutLinks == null || !cutLinks.contains(link)) {
            return link.extend(attribute);
        } else {
            return attribute.createInvalid();
        }
    }

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
        return attribute.compareTo(exclRoute.getAttribute()) < 0;
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Attribute attribute, PathAttribute path, Route exclRoute) {
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
