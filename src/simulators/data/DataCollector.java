package simulators.data;

import io.reporters.Reporter;
import core.Engine;

import java.io.IOException;

/**
 * Tag interface that all data collectors must implement.
 * A data collector is responsible for collecting data for a simulation.
 */
public interface DataCollector {

    /**
     * Registers the collector with the engine used for simulating.
     *
     * @param engine engine used for simulating.
     */
    void register(Engine engine);

    /**
     * Dumps the current data to the reporter.
     *
     * @param reporter reporter to dump data to.
     */
    void dump(Reporter reporter) throws IOException;

    /**
     * Clears all data from a data collector.
     */
    void clear();
}
