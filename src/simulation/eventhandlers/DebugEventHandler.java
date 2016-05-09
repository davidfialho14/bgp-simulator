package simulation.eventhandlers;

import simulation.events.*;

public class DebugEventHandler
        implements ImportListener, LearnListener, SelectListener, ExportListener, DetectListener {

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
        System.out.println("DETECT:\t" + event);
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        System.out.println("Export:\t" + event);
    }

    /**
     * Invoked when a import event occurs.
     *
     * @param event import event that occurred.
     */
    @Override
    public void onImported(ImportEvent event) {
        System.out.println("Import:\t" + event);
    }

    /**
     * Invoked when a learn event occurs.
     *
     * @param event learn event that occurred.
     */
    @Override
    public void onLearned(LearnEvent event) {
        System.out.println("Learn:\t" + event);
    }

    /**
     * Invoked when a select event occurs.
     *
     * @param event select event that occurred.
     */
    @Override
    public void onSelected(SelectEvent event) {
        System.out.println("Select:\t" + event);
    }
}
