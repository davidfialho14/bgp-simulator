package addons.eventhandlers;

import network.Link;
import simulation.Route;
import simulation.events.*;

import java.io.PrintStream;

public class DebugEventHandler
        implements ImportListener, LearnListener, SelectListener, ExportListener, DetectListener {

    private PrintStream printStream;    // print stream to print debug messages

    // listen to all events by default
    private boolean importEventsEnabled;
    private boolean learnEventsEnabled;
    private boolean selectEventsEnabled;
    private boolean detectEventsEnabled;
    private boolean exportEventsEnabled;

    /**
     * Creates a debug event handler that will print to the stdout by default.
     *
     * @param enabled sets all events as enabled or disabled.
     */
    public DebugEventHandler(boolean enabled) {
        this.printStream = System.out;
        setAllEnabled(enabled);
    }

    /**
     * Creates a debug event handler that will print to the given print stream.
     *
     * @param enabled sets all events as enabled or disabled.
     */
    public DebugEventHandler(PrintStream printStream, boolean enabled) {
        this.printStream = printStream;
        setAllEnabled(enabled);
    }

    public void setAllEnabled(boolean enabled) {
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
        if (detectEventsEnabled) {
            printStream.println("Detect:\t" + event.getDetectingNode() + " detected with " +
                                        pretty(event.getLearnedRoute()) + " learned from " +
                                        pretty(event.getOutLink()) + " other option was " +
                                        pretty(event.getExclRoute()));
        }
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        if (exportEventsEnabled) {
            printStream.println("Export:\t" + event.getExportingNode() + " exported to " + event.getLearningNode() +
                                        " " + pretty(event.getRoute()));
        }
    }

    /**
     * Invoked when a import event occurs.
     *
     * @param event import event that occurred.
     */
    @Override
    public void onImported(ImportEvent event) {
        if (importEventsEnabled) {
            printStream.println("Import:\t" + event.getImportingNode() + " imported from " + event.getExportingNode() +
                                        " " + pretty(event.getRoute()));
        }
    }

    /**
     * Invoked when a learn event occurs.
     *
     * @param event learn event that occurred.
     */
    @Override
    public void onLearned(LearnEvent event) {
        if (learnEventsEnabled) {
            printStream.println("Learn:\t" + event.getLearningNode() + " learned through " + pretty(event.getLink()) +
                                        " " + pretty(event.getRoute()));
        }
    }

    /**
     * Invoked when a select event occurs.
     *
     * @param event select event that occurred.
     */
    @Override
    public void onSelected(SelectEvent event) {
        if (selectEventsEnabled) {
            if (event.getPreviousRoute().equals(event.getSelectedRoute())) {
                printStream.println("Select:\t" + event.getSelectingNode() + " selected same " +
                                            pretty(event.getSelectedRoute()));
            } else {
                printStream.println("Select:\t" + event.getSelectingNode() + " selected " +
                                            pretty(event.getSelectedRoute()) + " over " +
                                            pretty(event.getPreviousRoute()));
            }
        }
    }

    /*
        Set of methods to print components in a prettier way.
     */

    private static String pretty(Route route) {
        return String.format("route (%s, %s)", route.getAttribute(), route.getPath());
    }

    private static String pretty(Link link) {
        return String.format("link (%s->%s, %s)", link.getSource(), link.getDestination(), link.getLabel());
    }
}
