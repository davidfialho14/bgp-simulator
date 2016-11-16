package core.exporters;

import core.Route;
import core.schedulers.RouteReference;
import core.topology.Node;

import java.util.HashMap;
import java.util.Map;

public class MinimumRouteAdvertisementTimer {

    // Minimum Route Advertisement Interval
    private static final int MRAI = 20;

    // simple class aggregating all information for one timer of one node
    private class NodeTimer {
        private long expirationTime = 0;
        private RouteReference exportedRouteReference = null;

        public NodeTimer(long expirationTime, Route exportRoute) {
            this.expirationTime = expirationTime;
            this.exportedRouteReference = new RouteReference(exportRoute);
        }
    }

    // structure storing the timers for each node
    private final Map<Node, NodeTimer> nodesTimers = new HashMap<>();

    /**
     * Checks if the MRA Timer for the given node has expired.
     *
     * @param node          node to check timer for
     * @param currentTime   current time to compare with the expiration time of the current timer
     * @return true if the timer is active, and false otherwise
     */
    public boolean hasExpired(Node node, long currentTime) {
        NodeTimer nodeTimer = nodesTimers.get(node);

        return nodeTimer == null || nodeTimer.expirationTime < currentTime;
    }

    /**
     * Returns the reference for the export route of the given node.
     *
     * @param node  node to get export route reference for
     * @return the reference for the export route of the given node
     */
    public RouteReference getExportRouteReference(Node node) {
        return nodesTimers.get(node).exportedRouteReference;
    }

    /**
     * Returns the expiration timer of the timer corresponding to the given node.
     *
     * @param node  node to get expiration time for
     * @return the expiration timer of the timer corresponding to the given node
     */
    public long getExpirationTime(Node node) {
        return nodesTimers.get(node).expirationTime;
    }

    /**
     * Resets the timer of the given node. Sets the expiration time according to the current time and sets
     * the export route to the given route.
     *
     * @param node          node to reset timer for
     * @param currentTime   current simulation time to base the expiration time on
     * @param exportRoute   route to set as the export route of the node
     */
    public void resetNodeTimer(Node node, long currentTime, Route exportRoute) {
        nodesTimers.put(node, new NodeTimer(currentTime + MRAI, exportRoute));
    }

    /**
     * Updates the export route of the node. This changes the export route for all routes already included
     * in the scheduler exported by the given node. Should be called every time a new route is exported and
     * the node's timer is still active.
     *
     * @param node  node to update route for
     * @param route route to update to
     */
    public void updateExportRoute(Node node, Route route) {
        nodesTimers.get(node).exportedRouteReference.setRoute(route);
    }

    /**
     * Resets the timers of all nodes to 0.
     */
    public void reset() {
        nodesTimers.clear();
    }

}
