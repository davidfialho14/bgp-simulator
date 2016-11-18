package v2.simulators;

import v2.core.Destination;
import v2.core.Engine;
import v2.core.Topology;
import v2.io.reporters.Reporter;
import v2.main.Parameters;

/**
 * A simulation setup defines the behaviour of the simulation.
 */
public abstract class Setup {

    protected final Engine engine;
    protected final Topology topology;
    protected final Destination destination;

    /**
     * Initializes a setup with the global requisites: an engine, topology, and destination.
     *
     * @param engine        engine to simulate with.
     * @param topology      topology to simulate.
     * @param destination   destination to simulate for.
     */
    public Setup(Engine engine, Topology topology, Destination destination) {
        this.engine = engine;
        this.topology = topology;
        this.destination = destination;
    }

    /**
     * Returns the data collector used to collect simulation data.
     *
     * @return the data collector used to collect simulation data.
     */
    public abstract DataCollector getDataCollector();

    /**
     * (Template Method)
     *
     * Called before starting each simulation. Subclasses should use this method to implement any necessary
     * initial setup before the simulation starts.
     */
    public abstract void setup();

    /**
     * Starts the simulation.
     */
    public final void start() {
        engine.simulate(topology, destination);
    }

    /**
     * (Template Method)
     *
     * Called after finishing a simulation instance. Subclasses should use this method to implement any
     * necessary final cleanup after the simulation finishes.
     */
    public abstract void cleanup();

    /**
     * Reports the setup parameters. (Visit method of a visitor pattern)
     *
     * @param reporter      reporter used to report (Visitor)
     * @param parameters    parameters of the simulator.
     */
    public abstract void report(Reporter reporter, Parameters parameters);

}
