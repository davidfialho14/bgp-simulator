package protocols;

import core.Protocol;
import core.Route;
import core.topology.Link;

/**
 * Implements the detection D2 and the reaction R1.
 */
public class D2R1Protocol extends Reaction1 implements Protocol, Detection2 {

    @Override
    public boolean isOscillation(Link link, Route learnedRoute, Route exclRoute) {
        return Detections.detection2(link.getSource(), learnedRoute, exclRoute);
    }

    @Override
    public void setParameters(Link link, Route learnedRoute, Route exclRoute) {
        setParameters(link, learnedRoute);
    }

    @Override
    public void reset() {
        destinationCutLinks.clear();
    }

    @Override
    public String toString() {
        return "D2R1";
    }

}
