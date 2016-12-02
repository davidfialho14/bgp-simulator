package simulators.basic;

import core.Attribute;
import core.Link;
import core.Path;
import core.events.*;
import io.reporters.Reporter;
import simulators.DataCollector;
import simulators.Dataset;
import simulators.DetectionData;

import java.io.IOException;
import java.util.Iterator;

/**
 * Collects all data that can be stored in a basic dataset.
 */
public class BasicDataCollector implements DataCollector, ExportListener, DetectListener,
        StartListener, AdvertisementListener, EndListener, ThresholdReachedListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final BasicDataset dataset = new BasicDataset();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new basic data collector and registers it with the event notifier to receive notifications
     * of all the required events.
     */
    public BasicDataCollector() {
        EventNotifier.eventNotifier().addExportListener(this);
        EventNotifier.eventNotifier().addDetectListener(this);
        EventNotifier.eventNotifier().addStartListener(this);
        EventNotifier.eventNotifier().addAdvertisementListener(this);
        EventNotifier.eventNotifier().addEndListener(this);
        EventNotifier.eventNotifier().addThresholdReachedListener(this);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Data Collector Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Gives access to the data set storing the collected data. The dataset implementation returned depends on the
     * collector implementation.
     *
     * @return a dataset instance with the collected data.
     */
    @Override
    public Dataset getDataset() {
        return dataset;
    }

    /**
     * Unregisters from the event notifier for all events.
     */
    @Override
    public void unregister() {
        EventNotifier.eventNotifier().removeExportListener(this);
        EventNotifier.eventNotifier().removeDetectListener(this);
        EventNotifier.eventNotifier().removeStartListener(this);
        EventNotifier.eventNotifier().removeAdvertisementListener(this);
        EventNotifier.eventNotifier().removeEndListener(this);
        EventNotifier.eventNotifier().removeThresholdReachedListener(this);
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        dataset.clear();
    }

    /**
     * Reports the current collected data using the given reporter implementation.
     *
     * @param reporter          reporter implementation to be used.
     * @param simulationNumber  number of the simulation being reported.
     */
    @Override
    public void report(Reporter reporter, int simulationNumber) throws IOException {
        reporter.report(simulationNumber, dataset);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Event Handling Methods - This methods are called during the simulation and should not be
     *  called elsewhere.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked when a detect event occurs. It checks if the detection was a false positive.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = event.getCycle();

        // start with the alternative route's attribute
        Attribute initialAttribute = event.getAlternativeRoute().getAttribute();

        // extend the attribute along the path
        Attribute attribute = initialAttribute;
        Iterator<Link> linkIterator = cycle.inLinksIterator();
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            attribute = link.getLabel().extend(link, attribute);
        }

        // it is a false positive if the alternative route's attribute when extended along the cycle
        // returns an attribute with equal or lower preference
        boolean falsePositive = (attribute.compareTo(initialAttribute) >= 0);

        dataset.addDetection(new DetectionData(event.getDetectingRouter(), event.getOutLink(), cycle,
                initialAttribute, falsePositive));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        dataset.addMessage();
    }

    /**
     * Invoked when a start event occurs. Stores the simulation seed.
     *
     * @param event start event that occurred.
     */
    @Override
    public void onStarted(StartEvent event) {
        dataset.setSimulationSeed(event.getSeed());
    }

    /**
     * Invoked when a advertisement event occurs.
     *
     * @param event advertisement event that occurred.
     */
    @Override
    public void onAdvertised(AdvertisementEvent event) {
        dataset.setLastMessageTime(event.getAdvertisingRouter(), event.getTimeInstant());
    }

    /**
     * Invoked when a end event occurs.
     *
     * @param event end event that occurred.
     */
    @Override
    public void onEnded(EndEvent event) {
        dataset.setSimulationTime(event.getTimeInstant());
    }

    /**
     * Invoked when a threshold reached event occurs.
     *
     * @param event threshold reached event that occurred.
     */
    @Override
    public void onThresholdReached(ThresholdReachedEvent event) {
        dataset.setProtocolTerminated(false);
    }

}
