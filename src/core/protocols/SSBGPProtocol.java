package core.protocols;

import core.*;
import core.events.*;
import core.exporters.Exporter;

/**
 * Implementation of the SS-BGP Protocol. The protocol can be configured to use any detection implementation.
 * Singleton Class!
 */
public class SSBGPProtocol implements Protocol {

    private static final SSBGPProtocol PROTOCOL = new SSBGPProtocol();

    private SSBGPProtocol() { } // use factory method!

    /**
     * Returns an SS-BGP protocol instance.
     *
     * @return an SS-BGP protocol instance.
     */
    public static SSBGPProtocol ssBGPProtocol() {
        return PROTOCOL;
    }

    /**
     * Processes a message that arrives at the given router.
     *
     * @param message  message to process.
     * @param exporter exporter used to export a route.
     */
    @Override
    public final void process(Message message, Exporter exporter) {
        int time = message.getArrivalTime();
        Link link = message.getTraversedLink();

        EventNotifier.eventNotifier().notifyArrivalEvent(new ArrivalEvent(time, message.getRoute(), link));

        Route importedRoute = importRoute(message.getRoute(), link);
        EventNotifier.eventNotifier().notifyImportEvent(new ImportEvent(time, importedRoute, link));

        Route learnedRoute = learn(link, importedRoute, time);
        EventNotifier.eventNotifier().notifyLearnEvent(new LearnEvent(time, link, learnedRoute));

        Router router = link.getSource();

        // store the previously selected route
        Route previousSelectedRoute = router.getTable().getSelectedRoute();

        router.getTable().setRoute(link.getTarget(), learnedRoute);

        if (router.getTable().selectedNewRoute()) { // checks if the selected route changed
            Route newSelectedRoute = router.getTable().getSelectedRoute();

            EventNotifier.eventNotifier().notifySelectEvent(
                    new SelectEvent(time, router, previousSelectedRoute, newSelectedRoute));

            exporter.export(router, newSelectedRoute, time);
        }

    }

    /**
     * Imports the route received from the given link. This is the first step in the 'process' execution.
     *
     * @return the imported route.
     */
    public Route importRoute(Route route, Link link) {

        if (link.isTurnedOff()) {
            return InvalidRoute.invalidRoute();
        }

        Attribute attribute = link.getLabel().extend(link, route.getAttribute());

        if (attribute == InvalidAttribute.invalidAttr()) {
            return InvalidRoute.invalidRoute();
        }

        Path path = Path.copy(route.getPath());
        path.add(link.getTarget());

        // return a new route instance to avoid changing a route of the sending router
        return new Route(attribute, path);
    }

    /**
     * Takes a route and checks if the route constitutes a loop and if so, it considers the route to be
     * invalid and checks and using the pre-configured detection method checks if there is a policy dispute.
     * If so, then it turns the link off. If the route does not constitute a loop then it returns the same
     * route as the imported route.
     *
     * @param link          link from which the route was received.
     * @param importedRoute imported route.
     * @param currentTime   arrival time of the processed message.
     * @return an invalid route if detected a loop or the imported route if did not detect a loop.
     */
    public Route learn(Link link, Route importedRoute, int currentTime) {
        Router learningRouter = link.getSource();
        Route learnedRoute = importedRoute;

        if (importedRoute.getPath().contains(learningRouter)) {
            // detected a loop

            // select the best route learned from all out-neighbours except the exporting neighbors
            Route alternativeRoute = learningRouter.getTable().getAlternativeRoute(link.getTarget());

            if (learningRouter.getDetection().isPolicyConflict(link, importedRoute, alternativeRoute)) {
                link.setTurnedOff(true);

                EventNotifier.eventNotifier().notifyDetectEvent(
                        new DetectEvent(currentTime, link, importedRoute, alternativeRoute));
            }

            learnedRoute = InvalidRoute.invalidRoute();
        }

        return learnedRoute;
    }

}
