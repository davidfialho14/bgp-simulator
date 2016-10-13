package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.Link;
import core.topology.Node;

/**
 * Implements the detection D1 and the reaction R1.
 */
public class D1R1Protocol implements Protocol {

    private final Reaction1 reaction = new Reaction1();

    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute) {
        return Detections.detection1(learnedRoute.getAttribute(), alternativeRoute.getAttribute());
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route alternativeRoute) {
        reaction.setParameters(link, learnedRoute);
    }

    @Override
    public Attribute extend(Node destination, Link link, Attribute attribute) {
        return reaction.extend(destination, link, attribute);
    }

    @Override
    public void reset() {
        reaction.reset();
    }

    @Override
    public String toString() {
        return "D1R1";
    }
}
