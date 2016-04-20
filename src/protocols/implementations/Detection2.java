package protocols.implementations;

import network.Node;
import policies.Attribute;
import simulation.PathAttribute;
import simulation.Route;

/**
 * Implements the detection D1. D1 considers an oscillation when the learned route is preferred
 * to the elected route.
 */
interface Detection2 {

    static boolean isOscillation(Node learningNode, Attribute attribute, PathAttribute path, Route exclRoute) {
        return attribute.compareTo(exclRoute.getAttribute()) < 0
                       && path.getPathAfter(learningNode).equals(exclRoute.getPath());
    }

}
