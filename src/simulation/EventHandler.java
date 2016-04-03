package simulation;

import network.Link;

public abstract class EventHandler {

    void onBeforeSimulate() {
        // by default do nothing
    }

    void onAfterSimulate() {
        // by default do nothing
    }

    void onBeforeLearn(Link link, Route exportedRoute) {
        // by default do nothing
    }

    void onAfterLearn(Link link, Route exportedRoute, Route learnedRoute) {
        // by default do nothing
    }

    void onBeforeSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                        Attribute prevSelectedAttribute, PathAttribute prevSelectedPath) {
        // by default do nothing
    }
    void onAfterSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                       Attribute prevSelectedAttribute, PathAttribute prevSelectedPath, Route selectedRoute) {
        // by default do nothing
    }

    void onBeforeExport(Link link, Route route, ScheduledRoute prevScheduledRoute) {
        // by default do nothing
    }

    void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute, ScheduledRoute scheduledRoute) {
        // by default do nothing
    }
}
