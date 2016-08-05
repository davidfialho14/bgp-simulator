package addons.eventhandlers;

public class MessageAndDetectionCountHandler implements core.events.ExportListener, core.events.DetectListener {

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
    public void register(core.events.SimulationEventGenerator eventGenerator) {
        eventGenerator.addExportListener(this);
        eventGenerator.addDetectListener(this);
    }

    // --- EVENTS ---------------------------------------------------------------------------------------------------

    @Override
    public void onExported(core.events.ExportEvent event) {
        messageCount++;
    }

    @Override
    public void onDetected(core.events.DetectEvent event) {
        detectionCount++;
    }
}