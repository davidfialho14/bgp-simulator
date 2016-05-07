package simulation;

import network.Link;
import policies.Attribute;

public abstract class EventHandler {

    protected void onBeforeSimulate() {
        // by default do nothing
    }

    protected void onAfterSimulate() {
        // by default do nothing
    }

    protected void onBeforeLearn(Link link, Route exportedRoute) {
        // by default do nothing
    }

    protected void onAfterLearn(Link link, Route exportedRoute, Route learnedRoute) {
        // by default do nothing
    }

    protected  void onBeforeSelect(NodeState nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                                   Attribute prevSelectedAttribute, PathAttribute prevSelectedPath) {
        // by default do nothing
    }
    protected  void onAfterSelect(NodeState nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                                  Attribute prevSelectedAttribute, PathAttribute prevSelectedPath, Route selectedRoute) {
        // by default do nothing
    }

    protected void onBeforeExport(Link link, Route route, ScheduledRoute prevScheduledRoute) {
        // by default do nothing
    }

    protected void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute,
                              ScheduledRoute scheduledRoute) {
        // by default do nothing
    }

    protected void onOscillationDetection(Link link, Route exportedRoute, Route learnedRoute, Route exclRoute) {
        // by default do nothing
    }

    protected void onBrokenLink(Link brokenLink) {
        // by default do nothing
    }
}
