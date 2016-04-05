package simulation.implementations.handlers;

import network.Link;
import policies.Attribute;
import simulation.*;

public class DebugEventHandler extends EventHandler {

    @Override
    public void onBeforeSimulate() {
        super.onBeforeSimulate();
        System.out.println("BEFORE SIMULATE");
    }

    @Override
    public void onAfterSimulate() {
        super.onAfterSimulate();
        System.out.println("AFTER SIMULATE");
    }

//    @Override
//    public void onBeforeLearn(Link link, Route exportedRoute) {
//        super.onBeforeLearn(link, exportedRoute);
//
//        System.out.println("BEFORE LEARN:\t" + link + "\t|\t" + exportedRoute);
//    }

    @Override
    public void onAfterLearn(Link link, Route exportedRoute, Route learnedRoute) {
        super.onAfterLearn(link, exportedRoute, learnedRoute);

        System.out.println("LEARN:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute);
    }

//    @Override
//    public void onBeforeSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
//                               Attribute prevSelectedAttribute, PathAttribute prevSelectedPath) {
//        super.onBeforeSelect(nodeStateInfo, link, exportedRoute, learnedRoute, prevSelectedAttribute, prevSelectedPath);
//
//        System.out.println("BEFORE SELECT:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute + "\t|\t"
//                + prevSelectedAttribute + "\t|\t" + prevSelectedPath);
//    }

    @Override
    public void onAfterSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                              Attribute prevSelectedAttribute, PathAttribute prevSelectedPath, Route selectedRoute) {
        super.onAfterSelect(nodeStateInfo, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath, selectedRoute);

        System.out.println("SELECT:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute  + "\t|\t"
                + prevSelectedAttribute + "\t|\t" + prevSelectedPath + "\t|\t" + selectedRoute);
    }

    @Override
    public void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute,
                              ScheduledRoute scheduledRoute) {
        super.onAfterExport(link, route, prevScheduledRoute, scheduledRoute);

        System.out.println("EXPORT:\t" + link + "\t|\t" + route);
    }

    @Override
    public void onDiscardRoute(Link link, Route route) {
        super.onDiscardRoute(link, route);

        System.out.println("DISCARDED:\t" + link + "\t|\t" + route);
    }

    @Override
    public void onOscillationDetection(Link link, Route exportedRoute, Route learnedRoute, Route exclRoute) {
        super.onOscillationDetection(link, exportedRoute, learnedRoute, exclRoute);

        System.out.println("DETECTED:\t" + link + "\t|\t" + learnedRoute + "\t|\t" + exclRoute);
    }
}
