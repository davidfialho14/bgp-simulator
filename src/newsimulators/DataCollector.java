package newsimulators;

import core.Engine;

/**
 * Data collector is the class responsible for collecting data during a simulation. Simulators delegate on data
 * collectors the data collection logic. To collect data from a simulation a data collector must be registered with a
 * simulation engine. After finishing the data collection the collector should be unregistered.
 */
public interface DataCollector {

    /**
     * Registers the collector with the simulation engine. The collector must be registered to collect data.
     * It is not possible to register a collector that is already registered. To register with a new engine, the
     * collector must unregister from the current engine before registering with a new one.
     *
     * @param engine engine used for simulating.
     * @throws IllegalStateException if the data collector is already registered.
     */
    void register(Engine engine) throws IllegalStateException;

    /**
     * Clears all data that has been collected
     */
    void clear();

    /**
     * Unregisters the collector from the registered engine. If the collector is not registered the method will take
     * no effect.
     */
    void unregister();

    /**
     * Gives access to the data set storing the collected data. The dataset implementation returned depends on the
     * collector implementation
     *
     * @return a dataset instance with the collected data.
     */
    Dataset getDataset();

}
