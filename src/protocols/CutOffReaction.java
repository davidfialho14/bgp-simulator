package protocols;

import core.Attribute;
import core.Route;
import core.topology.Link;

import java.util.HashSet;
import java.util.Set;

import static core.InvalidAttribute.invalidAttr;

/**
 * Implements a detection reaction where routes, learned from the neighbor announcing the looping route at the moment
 * of the detection, are no longer accepted (become invalid routes).
 */
public class CutOffReaction implements Reaction {

    // stores all links marked as bad links - the source node of this links does not accept routes from the destination
    protected Set<Link> badLinks = new HashSet<>();

    /**
     * Extends the attribute using the common 'extend' operation of the link. But if the destination node of the link
     * caused a detection, then it returns an invalid attribute every time.
     *
     * @param attribute   attribute to be extended.
     * @param link        link to extend the attribute.
     * @return extended attribute (new instance).
     */
    @Override
    public Attribute extend(Attribute attribute, Link link) {

        if (badLinks.contains(link)) {
            return invalidAttr();
        } else {
            return link.extend(attribute);
        }
    }

    /**
     * Takes the link from which the looping route was learned and marks the neighbor (destination node) so that it
     * does not accept anymore route from that neighbor.
     *
     * @param link             link from which the new route was learned.
     * @param learnedRoute     new learned route.
     * @param alternativeRoute most preferred route learned from other neighbor (not the destination node of the link)
     */
    @Override
    public void detectionInfo(Link link, Route learnedRoute, Route alternativeRoute) {
        badLinks.add(link);
    }

    /**
     * Clears all neighbors from which routes are not accepted. In other words,  after calling this method, routes
     * start to be accepted from all neighbors again.
     */
    @Override
    public void reset() {
        badLinks.clear();
    }
}
