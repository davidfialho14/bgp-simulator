package protocols;

import core.Attribute;
import core.Route;
import core.topology.Node;

/**
 * Provides static implementations multiple detection conditions.
 *
 * the detection D1. D1 considers an oscillation when the learned route is preferred
 * to the elected route.
 */
interface Detections {

    /**
     * Implementation of detection 1. Detection 1 detects if the learned route's attribute is preferred over the
     * attribute of the best route not learned from the same neighbour as the new learned route.
     *
     * @param learnedAttribute  attribute of the learned route.
     * @param exclAttribute     attribute of the elected route.
     * @return true if the detection condition is met.
     */
    static boolean detection1(Attribute learnedAttribute, Attribute exclAttribute) {
        return learnedAttribute.compareTo(exclAttribute) < 0;
    }

    /**
     * Implementation of detection 2. Detection 2 detects if the learned route's attribute is preferred over the
     * attribute of ExclRoute (the best route not learned from the same neighbour as the new learned route) <b>and</b>
     * the subpath of the learned route after the learning node is equal to the ExclRoute' path.
     *
     * @param learningNode  node the learned a new route.
     * @param learnedRoute  new learned route.
     * @param exclRoute     best route learned from any other but the neighbor of the new learned route.
     * @return true if the detection condition is met.
     */
    static boolean detection2(Node learningNode, Route learnedRoute, Route exclRoute) {
        return learnedRoute.getAttribute().compareTo(exclRoute.getAttribute()) < 0
                && learnedRoute.getPath().getPathAfter(learningNode).equals(exclRoute.getPath());
    }

}
