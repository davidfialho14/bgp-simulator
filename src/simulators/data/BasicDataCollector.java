package simulators.data;

import io.reporters.Reporter;
import simulation.Engine;
import simulation.TimeListener;
import simulation.events.DetectEvent;
import simulation.events.DetectListener;
import simulation.events.ExportEvent;
import simulation.events.ExportListener;

import java.io.IOException;

/**
 * Collects the data for a basic data set.
 */
public class BasicDataCollector implements DataCollector, ExportListener, DetectListener, TimeListener {

    protected BasicDataSet basicDataSet = new BasicDataSet();

    /**
     * Registers the collector with the engine used for simulating.
     *
     * @param engine engine used for simulating.
     */
    @Override
    public void register(Engine engine) {
        engine.getEventGenerator().addExportListener(this);
        engine.getEventGenerator().addDetectListener(this);
        engine.timeProperty().addListener(this);
    }

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to dump data to.
     */
    @Override
    public void dump(Reporter reporter) throws IOException {
        reporter.dump(basicDataSet);
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
    public void onDetected(DetectEvent event) {
        basicDataSet.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), DetectEvent.getCycle(event)));
    }

    /**
     * Invoked when a export event occurs.
     *
     * @param event export event that occurred.
     */
    @Override
    public void onExported(ExportEvent event) {
        basicDataSet.addMessage();
    }

    @Override
    public void onTimeChange(long newTime) {
        basicDataSet.setSimulationTime(newTime);
    }
}
