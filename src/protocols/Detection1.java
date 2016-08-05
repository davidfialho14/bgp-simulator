package protocols;

import policies.Attribute;
import core.Route;

/**
 * Implements the detection D1. D1 considers an oscillation when the learned route is preferred
 * to the elected route.
 */
interface Detection1 {

    static boolean isOscillation(Attribute attribute, Route exclRoute) {
        return attribute.compareTo(exclRoute.getAttribute()) < 0;
    }

}
