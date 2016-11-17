package v2.core.protocols;

import v2.core.*;

import static v2.core.InvalidAttribute.invalidAttr;
import static v2.core.InvalidRoute.invalidRoute;

/**
 * Implementation of the SS-BGP Protocol. The protocol can be configured to use any detection implementation.
 */
public class SSBGPProtocol implements Protocol {

    private final Detection detection;

    /**
     * Pre-configures the SS-BGP protocol with the given detection implementation.
     *
     * @param detection detection implementation to use to detect policy conflicts.
     */
    public SSBGPProtocol(Detection detection) {
        this.detection = detection;
    }

    /**
     * Processes a message that arrives at the given router.
     *
     * @param message  message to process.
     * @param exporter exporter used to export a route.
     */
    @Override
    public final void process(Message message, Exporter exporter) {
        Link link = message.getTraversedLink();

        Route importedRoute = importRoute(message.getRoute(), link);
        Route learnedRoute = learn(importedRoute, link);

        Router learningRouter = link.getSource();
        learningRouter.getTable().setRoute(link.getTarget(), learnedRoute);

        if (learningRouter.getTable().selectedNewRoute()) { // checks if the selected route changed
            exporter.exportToNeighbors(learningRouter, learningRouter.getTable().getSelectedRoute());
        }

    }

    /**
     * Imports the route received from the given link. This is the first step in the 'process' execution.
     *
     * @return the imported route.
     */
    public Route importRoute(Route route, Link link) {

        if (link.isTurnedOff()) {
            return invalidRoute();
        }

        Attribute attribute = link.getLabel().extend(link, route.getAttribute());

        if (attribute == invalidAttr()) {
            return invalidRoute();
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
     * @param importedRoute imported route.
     * @param link          link from which the route was received.
     * @return an invalid route if detected a loop or the imported route if did not detect a loop.
     */
    public Route learn(Route importedRoute, Link link) {
        Router learningRouter = link.getSource();
        Route learnedRoute = importedRoute;

        if (importedRoute.getPath().contains(learningRouter)) {
            // detected a loop

            // select the best route learned from all out-neighbours except the exporting neighbors
            Route alternativeRoute = learningRouter.getTable().getAlternativeRoute(link.getTarget());

            if (detection.isPolicyConflict(link, importedRoute, alternativeRoute)) {
                link.setTurnedOff(true);
            }

            learnedRoute = invalidRoute();
        }

        return learnedRoute;
    }

}
