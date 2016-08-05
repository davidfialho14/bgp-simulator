package protocols;

import network.Node;
import core.Attribute;
import core.Path;
import core.Route;

/**
 * Implements the detection D1. D1 considers an oscillation when the learned route is preferred
 * to the elected route.
 */
interface Detection2 {

    static boolean isOscillation(Node learningNode, Attribute attribute, Path path, Route exclRoute) {
        return attribute.compareTo(exclRoute.getAttribute()) < 0
                       && path.getPathAfter(learningNode).equals(exclRoute.getPath());
    }

}
