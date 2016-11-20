package core;


import core.events.EndEvent;
import core.events.EventNotifier;
import core.events.StartEvent;
import core.events.TerminateEvent;
import core.exporters.Exporter;
import core.schedulers.Scheduler;

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

        EventNotifier.eventNotifier().notifyStartEvent(new StartEvent(0, scheduler));

        // start the simulation by having the destination export its self route to its neighbors
        exporter.export(destination, topology.getPolicy());

        int time = 0;
        while (scheduler.hasMessages()) {

            Message message = scheduler.nextMessage();
            time = message.getArrivalTime();

            message.getTarget().process(message, exporter);

            if (!scheduler.hasMessages()) {
                // a terminate even is fired here to allow external components to add more messages if
                // necessary to the scheduler
                EventNotifier.eventNotifier().notifyTerminateEvent(new TerminateEvent(time));
            }
        }

        EventNotifier.eventNotifier().notifyEndEvent(new EndEvent(time));
    }

}
