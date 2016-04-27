package simulation.implementations.handlers;

import network.Link;
import policies.Attribute;
import simulation.*;

public class DebugEventHandler extends EventHandler {

    @Override
    protected void onBeforeSimulate() {
        super.onBeforeSimulate();
        System.out.println("BEFORE SIMULATE");
    }

    @Override
    protected void onAfterSimulate() {
        super.onAfterSimulate();
        System.out.println("AFTER SIMULATE");
    }

//    @Override
//    protected void onBeforeLearn(Link link, Route exportedRoute) {
//        super.onBeforeLearn(link, exportedRoute);
//
//        System.out.println("BEFORE LEARN:\t" + link + "\t|\t" + exportedRoute);
//    }

    @Override
    protected void onAfterLearn(Link link, Route exportedRoute, Route learnedRoute) {
        super.onAfterLearn(link, exportedRoute, learnedRoute);

        System.out.println("LEARN:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute);
    }

//    @Override
//    protected void onBeforeSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
//                               Attribute prevSelectedAttribute, PathAttribute prevSelectedPath) {
//        super.onBeforeSelect(nodeStateInfo, link, exportedRoute, learnedRoute, prevSelectedAttribute, prevSelectedPath);
//
//        System.out.println("BEFORE SELECT:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute + "\t|\t"
//                + prevSelectedAttribute + "\t|\t" + prevSelectedPath);
//    }

    @Override
    protected void onAfterSelect(NodeStateInfo nodeStateInfo, Link link, Route exportedRoute, Route learnedRoute,
                              Attribute prevSelectedAttribute, PathAttribute prevSelectedPath, Route selectedRoute) {
        super.onAfterSelect(nodeStateInfo, link, exportedRoute, learnedRoute,
                prevSelectedAttribute, prevSelectedPath, selectedRoute);

        System.out.println("SELECT:\t" + link + "\t|\t" + exportedRoute + "\t|\t" + learnedRoute  + "\t|\t"
                + prevSelectedAttribute + "\t|\t" + prevSelectedPath + "\t|\t" + selectedRoute);
    }

    @Override
    protected void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute,
                              ScheduledRoute scheduledRoute) {
        super.onAfterExport(link, route, prevScheduledRoute, scheduledRoute);

        System.out.println("EXPORT:\t" + link + "\t|\t" + route);
    }

    @Override
    protected void onOscillationDetection(Link link, Route exportedRoute, Route learnedRoute, Route exclRoute) {
        super.onOscillationDetection(link, exportedRoute, learnedRoute, exclRoute);

        System.out.println("DETECTED:\t" + link + "\t|\t" + learnedRoute + "\t|\t" + exclRoute);
    }

    @Override
    protected void onBrokenLink(Link brokenLink) {
        super.onBrokenLink(brokenLink);

        System.out.println("BROKE LINK:\t" + brokenLink);
    }
}
