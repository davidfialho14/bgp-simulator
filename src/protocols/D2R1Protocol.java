package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.Link;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol implements Protocol {

    private final Reaction1 reaction = new Reaction1();

    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute) {
        return Detections.detection2(link.getSource(), learnedRoute, alternativeRoute);
    }

    @Override
    public void detectionInfo(Link link, Route learnedRoute, Route alternativeRoute) {
        reaction.setParameters(link, learnedRoute);
    }

    @Override
    public Attribute extend(Attribute attribute, Link link) {
        return reaction.extend(attribute, link);
    }

    @Override
    public void reset() {
        reaction.reset();
    }

    @Override
    public String toString() {
        return "D2R1";
    }

}
