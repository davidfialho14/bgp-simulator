package addons.eventhandlers;

import simulation.events.*;

public class MessageAndDetectionCountHandler implements ExportListener, DetectListener {

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

    /**
     * Registers the event handler in the given event generator.
     *
     * @param eventGenerator event generator to register on.
     */
    public void register(SimulationEventGenerator eventGenerator) {
        eventGenerator.addExportListener(this);
        eventGenerator.addDetectListener(this);
    }

    // --- EVENTS ---------------------------------------------------------------------------------------------------

    @Override
    public void onExported(ExportEvent event) {
        messageCount++;
    }

    @Override
    public void onDetected(DetectEvent event) {
        detectionCount++;
    }
}