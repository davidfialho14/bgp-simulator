package protocols;

import core.NodeState;
import core.Route;
import core.topology.Link;

/**
 * Implementation of the neighbor detection which requires two conditions:
 *  - the attribute of the learned route must be preferred to the attribute of the alternative route (same as simple
 *  detection)
 *  - learned route comes from the last selected neighbor
 */
public class NeighborDetection implements Detection {

    /**
     * Checks if the conditions to detect a policy-based oscillation is verified. This should called every time a new
     * route containing loop is learned.
     *
     * @param link             link from which the new route was learned.
     * @param learnedRoute     new learned route.
     * @param alternativeRoute most preferred route learned from other neighbor (not the destination node of the link)
     * @param nodeState        state of the detecting node before learning the new route.
     * @return true if the detection conditions are verified and false otherwise.
     */
    @Override
    public boolean isPolicyDispute(Link link, Route learnedRoute, Route alternativeRoute, NodeState nodeState) {
        return learnedRoute.getAttribute().compareTo(alternativeRoute.getAttribute()) < 0
                && nodeState.getTable().getSelectedNeighbour().equals(link.getDestination());
    }

}
