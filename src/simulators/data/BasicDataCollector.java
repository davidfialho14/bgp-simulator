package simulators.data;

import core.Engine;
import core.TimeListener;
import io.reporters.Reporter;

import java.io.IOException;

/**
 * Collects the data for a basic data set.
 */
public class BasicDataCollector implements DataCollector, core.events.ExportListener, core.events.DetectListener, TimeListener, core.events.StartListener {

    protected BasicDataSet basicDataSet = new BasicDataSet();
    private Engine engine;

    /**
     * Registers the collector with the engine used for simulating.
     *
     * @param engine engine used for simulating.
     */
    @Override
    public void register(Engine engine) {
        this.engine = engine;
        this.engine.getEventGenerator().addExportListener(this);
        this.engine.getEventGenerator().addDetectListener(this);
        this.engine.getEventGenerator().addStartListener(this);
    }

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to write data to.
     */
    @Override
    public void dump(Reporter reporter) throws IOException {
        reporter.write(basicDataSet);
    }

    /**
     * Clears all data from a data collector.
     */
    @Override
    public void clear() {
        basicDataSet.clear();
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(core.events.DetectEvent event) {
        basicDataSet.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), core.events.DetectEvent.getCycle(event)));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(core.events.ExportEvent event) {
        basicDataSet.addMessage();
    }

    @Override
    public void onTimeChange(long newTime) {
        basicDataSet.setSimulationTime(newTime);
    }

    /**
     * Invoked when a start event occurs.
     *
     * @param event start event that occurred.
     */
    @Override
    public void onStarted(core.events.StartEvent event) {
        engine.timeProperty().addListener(this);
    }
}
