package simulation;

import network.Link;

public abstract class EventHandler {

    public void onBeforeSimulate() {
        // by default do nothing
    }

    public void onAfterSimulate() {
        // by default do nothing
    }

    public void onBeforeLearn(Link link, Route exportedRoute) {
        // by default do nothing
    }

    public void onAfterLearn(Link link, Route exportedRoute, Route learnedRoute) {
        // by default do nothing
    }

   public  void onBeforeSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                        Attribute prevSelectedAttribute, PathAttribute prevSelectedPath) {
        // by default do nothing
    }
   public  void onAfterSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                       Attribute prevSelectedAttribute, PathAttribute prevSelectedPath, Route selectedRoute) {
        // by default do nothing
    }

    public void onBeforeExport(Link link, Route route, ScheduledRoute prevScheduledRoute) {
        // by default do nothing
    }

    public void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute,
                              ScheduledRoute scheduledRoute) {
        // by default do nothing
    }

    public void onDiscardRoute(Link link, Route route) {
        // by default do nothing
    }
}
