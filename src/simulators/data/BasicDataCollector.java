package simulators.data;

import policies.Path;
import simulation.Engine;
import simulation.events.DetectEvent;
import simulation.events.DetectListener;
import simulation.events.ExportEvent;
import simulation.events.ExportListener;

/**
 * Collects the data for a basic data set.
 */
public class BasicDataCollector implements ExportListener, DetectListener {

    private BasicDataSet dataset;

    /**
     * Associates the collector with a basic dataset to store the collected data and registers the
     * collector with the engine that will simulate.
     *
     * @param engine  engine used for simulation to collect that for.
     * @param dataset dataset where to store data.
     */
    public BasicDataCollector(Engine engine, BasicDataSet dataset) {
        this.dataset = dataset;

        engine.getEventGenerator().addExportListener(this);
        engine.getEventGenerator().addDetectListener(this);
    }

    public BasicDataSet getDataset() {
        return dataset;
    }

    /**
     * Sets a new dataset where to store new collected data. After calling this method all new
     * data will be added to the new data set.
     *
     * @param dataset new dataset where to store new collected data.
     */
    public void setDataset(BasicDataSet dataset) {
        this.dataset = dataset;
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        Path cycle = event.getLearnedRoute().getPath().getSubPathBefore(event.getDetectingNode());
        cycle.add(event.getDetectingNode());    // include the detecting node in the start and end of the cycle path

        dataset.addDetection(new Detection(event.getDetectingNode(), event.getOutLink(), cycle));
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
}
