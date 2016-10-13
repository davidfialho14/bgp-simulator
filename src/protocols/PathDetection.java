package protocols;

import core.NodeState;
import core.Route;
import core.topology.ConnectedNode;
import core.topology.Link;

/**
 * Implementation of the path detection which requires two conditions:
 *  - the attribute of the learned route must be preferred to the attribute of the alternative route (same as simple
 *  detection)
 *  - sub-path of the learned route's path, between the node and the destination, must match the path of the
 *  alternative route.
 */
public class PathDetection implements Detection {

    /**
     * Checks if the attribute of the learned route is preferred to the attribute of the alternative route and the
     * sub-path of the learned route's path, between the node and the destination, matches the path of the
     * alternative route.
     *
     * @param link             ignored
     * @param learnedRoute     new learned route.
     * @param alternativeRoute most preferred route learned from other neighbor (not the destination node of the link)
     * @param nodeState        ignored
     * @return true if the detection conditions are verified.
     */
    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute, NodeState nodeState) {
        ConnectedNode learningNode = link.getSource();

        return learnedRoute.getAttribute().compareTo(alternativeRoute.getAttribute()) < 0
                && learnedRoute.getPath().getPathAfter(learningNode).equals(alternativeRoute.getPath());
    }

    @Override
    public String toString() {
        return "D2";
    }

}
