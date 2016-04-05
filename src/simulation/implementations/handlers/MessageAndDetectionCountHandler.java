package simulation.implementations.handlers;

import network.Link;
import simulation.EventHandler;
import simulation.Route;
import simulation.ScheduledRoute;

public class MessageAndDetectionCountHandler extends EventHandler {

    private int messageCount = 0;
    private int detectionCount = 0;

    // --- PUBLIC INTERFACE -----------------------------------------------------------------------------------------

    /**
     * Returns the current message count.
     * @return current message count.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Returns the current detection count.
     * @return current detection count.
     */
    public int getDetectionCount() {
        return detectionCount;
    }

    // --- EVENTS ---------------------------------------------------------------------------------------------------


    @Override
    protected void onAfterExport(Link link, Route route, ScheduledRoute prevScheduledRoute, ScheduledRoute scheduledRoute) {
        super.onAfterExport(link, route, prevScheduledRoute, scheduledRoute);
        messageCount++;
    }

    @Override
    protected void onOscillationDetection(Link link, Route exportedRoute, Route learnedRoute, Route exclRoute) {
        super.onOscillationDetection(link, exportedRoute, learnedRoute, exclRoute);
        detectionCount++;
    }
}
