package v2.core;


import v2.core.exporters.Exporter;
import v2.core.schedulers.Scheduler;

/**
 * Engine implements the hard simulation simulation logic.
 */
public class Engine {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields defining the engine's state
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final Exporter exporter;
    private final Scheduler scheduler;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a new engine and assigns it the given exporter. The exporter already include the scheduler
     * that is gonna be used by the engine.
     *
     * @param exporter  exporter used to export routes.
     */
    public Engine(Exporter exporter) {
        this.exporter = exporter;
        this.scheduler = exporter.getScheduler();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns the scheduler being used by the engine.
     *
     * @return the scheduler being used by the engine.
     */
    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * Entry point for each simulation instance. Simulates the topology given. Starts by advertising the
     * self routes of the destination.
     *
     * @param topology      topology to simulate.
     * @param destination   destination to simulate for.
     */
    public void simulate(Topology topology, Destination destination) {
        scheduler.reset();

        // FIXME fire start even here

        // start the simulation by having the destination export its self route to its neighbors
        exporter.export(destination, topology.getPolicy());

        while (scheduler.hasMessages()) {

            Message message = scheduler.nextMessage();
            message.getTarget().process(message);

            if (!scheduler.hasMessages()) {
                // a terminate even is fired here to allow external components to add more messages if
                // necessary to the scheduler
                // FIXME fire terminate even here
            }
        }

        // FIXME fire end even here
    }

}
