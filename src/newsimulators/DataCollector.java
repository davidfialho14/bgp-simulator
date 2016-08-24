package newsimulators;

import core.Engine;
import io.reporters.Reporter;

/**
 * Data collector is the class responsible for collecting data during a simulation. Simulators delegate on data
 * collectors the data collection logic. To collect data from a simulation a data collector must be registered with a
 * simulation engine. After finishing the data collection the collector should be unregistered. The data collector
 * can be visited by a reporter to report its data.
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
     * Reports the current collected data using the given reporter implementation.
     *
     * @param reporter reporter implementation to be used.
     */
    void report(Reporter reporter);

}
