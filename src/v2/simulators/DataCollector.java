package v2.simulators;

import v2.io.reporters.Reporter;

import java.io.IOException;

/**
 * Data collector is the class responsible for collecting data during a simulation. Simulators delegate on data
 * collectors the data collection logic. To collect data from a simulation a data collector must be registered with a
 * simulation engine. After finishing the data collection the collector should be unregistered. The data
 * collector can be visited by a reporter to report its data.
 */
public interface DataCollector {

    /**
     * Gives access to the data set storing the collected data. The dataset implementation returned depends on the
     * collector implementation
     *
     * @return a dataset instance with the collected data.
     */
    Dataset getDataset();

    /**
     * Clears all data that has been collected
     */
    void clear();

    /**
     * Reports the current collected data using the given reporter implementation.
     *
     * @param reporter reporter implementation to be used.
     */
    void report(Reporter reporter) throws IOException;

}
