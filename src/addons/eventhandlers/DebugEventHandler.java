package addons.eventhandlers;

import simulation.events.*;

public class DebugEventHandler
        implements ImportListener, LearnListener, SelectListener, ExportListener, DetectListener {

    // listen to all events by default
    private boolean importEventsEnabled = true;
    private boolean learnEventsEnabled = true;
    private boolean selectEventsEnabled = true;
    private boolean detectEventsEnabled = true;
    private boolean exportEventsEnabled = true;

    public DebugEventHandler(boolean enabled) {
        importEventsEnabled = enabled;
        learnEventsEnabled = enabled;
        selectEventsEnabled = enabled;
        detectEventsEnabled = enabled;
        exportEventsEnabled = enabled;
    }

    public void setImportEnabled(boolean enable) {
        importEventsEnabled = enable;
    }

    public void setLearnEnabled(boolean enable) {
        learnEventsEnabled = enable;
    }

    public void setSelectEnabled(boolean enable) {
        selectEventsEnabled = enable;
    }

    public void setDetectEnabled(boolean enable) {
        detectEventsEnabled = enable;
    }

    public void setExportEnabled(boolean enable) {
        exportEventsEnabled = enable;
    }

    public void register(SimulationEventGenerator eventGenerator) {
        eventGenerator.addImportListener(this);
        eventGenerator.addLearnListener(this);
        eventGenerator.addSelectListener(this);
        eventGenerator.addExportListener(this);
        eventGenerator.addDetectListener(this);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        if (detectEventsEnabled)
            System.out.println("DETECT:\t" + event);
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        if (exportEventsEnabled)
            System.out.println("Export:\t" + event);
    }

    /**
     * Invoked when a import event occurs.
     *
     * @param event import event that occurred.
     */
    @Override
    public void onImported(ImportEvent event) {
        if (importEventsEnabled)
            System.out.println("Import:\t" + event);
    }

    /**
     * Invoked when a learn event occurs.
     *
     * @param event learn event that occurred.
     */
    @Override
    public void onLearned(LearnEvent event) {
        if (learnEventsEnabled)
            System.out.println("Learn:\t" + event);
    }

    /**
     * Invoked when a select event occurs.
     *
     * @param event select event that occurred.
     */
    @Override
    public void onSelected(SelectEvent event) {
        if (selectEventsEnabled)
            System.out.println("Select:\t" + event);
    }
}
