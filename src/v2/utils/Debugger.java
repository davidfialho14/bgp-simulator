package v2.utils;


import v2.core.events.*;

import static v2.core.events.EventNotifier.eventNotifier;

public class Debugger implements StartListener, ImportListener, LearnListener, SelectListener, 
        DetectListener, ExportListener, AdvertisementListener, TerminateListener, EndListener {

    private static final Debugger DEBUGGER = new Debugger();

    private Debugger() {} // use factory method!

    public static void enableDebugger() {

        eventNotifier().addStartListener(DEBUGGER);
        eventNotifier().addImportListener(DEBUGGER);
        eventNotifier().addLearnListener(DEBUGGER);
        eventNotifier().addSelectListener(DEBUGGER);
        eventNotifier().addDetectListener(DEBUGGER);
        eventNotifier().addExportListener(DEBUGGER);
        eventNotifier().addAdvertisementListener(DEBUGGER);
        eventNotifier().addTerminateListener(DEBUGGER);
        eventNotifier().addEndListener(DEBUGGER);

    }

    private void print(SimulationEvent event, String message) {
        System.out.println(String.format("(%6d) %s", event.getTimeInstant(), message));
    }
    
    /**
     * Invoked when a end event occurs.
     *
     * @param event end event that occurred.
     */
    @Override
    public void onEnded(EndEvent event) {
        print(event, "End");
    }

    /**
     * Invoked when a learn event occurs.
     *
     * @param event learn event that occurred.
     */
    @Override
    public void onLearned(LearnEvent event) {
        print(event, String.format("Learn:\t\tRouter %d learned from %d route %s",
                event.getLearningRouter().getId(), 
                event.getExportingRouter().getId(),
                event.getRoute()));
    }

    /**
     * Invoked when a start event occurs.
     *
     * @param event start event that occurred.
     */
    @Override
    public void onStarted(StartEvent event) {
        print(event, "Start");
    }

    /**
     * Invoked when a import event occurs.
     *
     * @param event import event that occurred.
     */
    @Override
    public void onImported(ImportEvent event) {
        print(event, String.format("Import:\tRouter %d imported from %d with label %s route %s",
                event.getImportingRouter().getId(),
                event.getExportingRouter().getId(),
                event.getLink().getLabel(),
                event.getRoute()));
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        print(event, String.format("Detect:\tRouter %d detected with route %s having alternative %s",
                event.getDetectingRouter().getId(),
                event.getLearnedRoute(),
                event.getAlternativeRoute()));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        print(event, String.format("Export:\tRouter %d exported %s to %d",
                event.getExportingRouter().getId(),
                event.getRoute(),
                event.getLearningRouter().getId()));
    }

    /**
     * Invoked when a select event occurs.
     *
     * @param event select event that occurred.
     */
    @Override
    public void onSelected(SelectEvent event) {
        print(event, String.format("Select:\tRouter %d selected route %s with previous %s",
                event.getSelectingRouter().getId(),
                event.getSelectedRoute(),
                event.getPreviousRoute()));
    }

    /**
     * Invoked when a terminate event occurs.
     *
     * @param event terminate event that occurred.
     */
    @Override
    public void onTerminated(TerminateEvent event) {
        print(event, "Terminate");
    }

    /**
     * Invoked when a advertisement event occurs.
     *
     * @param event advertisement event that occurred.
     */
    @Override
    public void onAdvertised(AdvertisementEvent event) {
        print(event, String.format("Advertise:\tRouter %d advertised route %s",
                event.getAdvertisingRouter().getId(),
                event.getRoute()));
    }
}
