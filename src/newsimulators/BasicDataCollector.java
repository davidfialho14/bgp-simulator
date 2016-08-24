package newsimulators;

import core.Engine;
import core.TimeListener;
import core.events.DetectEvent;
import core.events.DetectListener;
import core.events.ExportEvent;
import core.events.ExportListener;
import simulators.data.Detection;

/**
 * Collects all data that can be stored in a basic dataset.
 */
public class BasicDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final BasicDataSet dataSet = new BasicDataSet();
    private Engine engine = null;    // registered engine

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

        if (this.engine != null) {
            throw new IllegalStateException("can not register collector that is already registered");
        }

        this.engine = engine;
        this.engine.getEventGenerator().addExportListener(this);
        this.engine.getEventGenerator().addDetectListener(this);
        this.engine.timeProperty().addListener(this);

    }

    /**
     * Unregisters the collector as an export, detect, and time listener of the engine. If the collector is not
     * registered the method will take no effect.
     */
    @Override
    public void unregister() {

        if (engine != null) {
            this.engine.getEventGenerator().removeExportListener(this);
            this.engine.getEventGenerator().removeDetectListener(this);
            this.engine.timeProperty().removeListener(this);
            this.engine = null;
        }
    }

    /**
     * Clears all data that has been collected.
     */
    @Override
    public void clear() {
        dataSet.clear();
    }

    /**
     * Gives access to the data set storing the collected data. The dataset implementation returned depends on the
     * collector implementation.
     *
     * @return a dataset instance with the collected data.
     */
    @Override
    public Dataset getDataset() {
        return dataSet;
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
        dataSet.setSimulationTime(newTime);
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        dataSet.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), DetectEvent.getCycle(event)));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        dataSet.addMessage();
    }

}
