package protocols;

import core.Attribute;
import core.InvalidAttribute;
import core.Route;
import core.topology.Link;

import java.util.HashSet;
import java.util.Set;

/**
 * Implements the reaction R1. R1 cuts the link completely not accepting more routes for that
 * destination from that link.
 */
class Reaction1 {

    protected Set<Link> cutLinks = new HashSet<>();

    public Attribute extend(Attribute attribute, Link link) {
        if (!cutLinks.contains(link)) {
            return link.extend(attribute);
        } else {
            return InvalidAttribute.invalidAttr();
        }
    }

    public void setParameters(Link link, Route learnedRoute) {
        // add the link to the list of cut links for this destination
        cutLinks.add(link);
    }

    public void reset() {
        cutLinks.clear();
    }

}
