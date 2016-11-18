package v2.simulators;

import v2.core.Destination;
import v2.core.Engine;
import v2.core.Topology;
import v2.io.reporters.Reporter;
import v2.main.Parameters;

public abstract class Setup {

    protected final Engine engine;
    protected final Topology topology;
    protected final Destination destination;

    public Setup(Engine engine, Topology topology, Destination destination) {
        this.engine = engine;
        this.topology = topology;
        this.destination = destination;
    }

    public abstract DataCollector getDataCollector();

    public abstract void setup();

    public abstract void start();

    public abstract void cleanup();

    public abstract void report(Reporter reporter, Parameters parameters);

}
