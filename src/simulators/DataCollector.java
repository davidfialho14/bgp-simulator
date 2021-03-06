package simulators;

/**
 * Data collector is the class responsible for collecting data during a simulation. Simulation setups delegate
 * on data collectors the data collection logic. To collect data from a simulation a data collector
 * registers with the event notifier during the construction process. A data collector can also unregister.
 * After finishing the data collection the collector should unregister. The data collector can be visited
 * by a reporter to report its data.
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
     * Unregisters from the event notifier for all events.
     */
    void unregister();

    /**
     * Clears all data that has been collected
     */
    void clear();

}
