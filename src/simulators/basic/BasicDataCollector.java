package simulators.basic;

import core.Attribute;
import core.Engine;
import core.Path;
import core.TimeListener;
import core.events.*;
import core.topology.Link;
import registers.Registration;
import simulators.DataCollector;
import simulators.Dataset;
import simulators.Detection;

import java.util.Iterator;

import static registers.Registration.noRegistration;
import static registers.Registration.registrationFor;

/**
 * Collects all data that can be stored in a basic dataset.
 */
public class BasicDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener, StartListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected final BasicDataset dataset;
    private Registration registration = noRegistration();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates new basic data collector with an empty basic dataset.
     */
    public BasicDataCollector() {
        dataset = new BasicDataset();
    }

    /**
     * Creates a new basic dataset with the given basic dataset. The collector will store the collected data in the
     * given dataset. This can be used by subclasses to define their own basic dataset
     *
     * @param basicDataset basic dataset to store the collected data.
     */
    public BasicDataCollector(BasicDataset basicDataset) {
        dataset = basicDataset;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Data Collector Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Registers the collector as an export, detect, and time listener of the engine.
     *
     * @param engine engine used for simulating.
     * @throws IllegalStateException if the data collector is already registered.
     */
    @Override
    public void register(Engine engine) throws IllegalStateException {
        if (registration.isRegistered()) {
            throw new IllegalStateException("can not register collector that is already registered");
        }

        registration = registrationFor(engine, this)
                .exportEvents()
                .detectEvents()
                .timeEvents()
                .startEvents()
                .register();
    }

    /**
     * Unregisters the collector as an export, detect, and time listener of the engine. If the collector is not
     * registered the method will take no effect.
     */
    @Override
    public void unregister() {
        registration.cancel();
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        dataset.clear();
    }

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

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Event Handling Methods - This methods are called during the simulation and should not be
     *  called elsewhere.
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Invoked the time property of the engine changes
     *
     * @param newTime the new time value
     */
    @Override
    public void onTimeChange(long newTime) {
        dataset.setSimulationTime(newTime);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = event.getCycle();

        // start with the selected attribute of the first node
        Attribute initialAttribute = event.getLearnedRoute().getSelectedAttribute(cycle.getSource());
        Attribute attribute = initialAttribute;

        // extend the attribute along the path
        Iterator<Link> linkIterator = cycle.inLinksIterator();
        while (linkIterator.hasNext()) {
            attribute = linkIterator.next().extend(attribute);
        }

        // it's guaranteed a false positive if the extend attribute along the cycle is not better the initial attribute
        boolean falsePositive = (attribute.compareTo(initialAttribute) >= 0);

        dataset.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), cycle,
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
}
