package protocols;

import core.Attribute;
import core.Protocol;
import core.Route;
import core.topology.ConnectedNode;
import core.topology.Link;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol implements Protocol {

    private final Reaction1 reaction = new Reaction1();

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return Detections.detection2(link.getSource(), learnedRoute, exclRoute);
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        reaction.setParameters(link, learnedRoute);
    }

    @Override
    public Attribute extend(ConnectedNode destination, Link link, Attribute attribute) {
        return reaction.extend(destination, link, attribute);
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
