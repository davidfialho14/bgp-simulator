package core;


import core.events.EndEvent;
import core.events.StartEvent;
import core.events.TerminateEvent;
import core.events.ThresholdReachedEvent;
import core.exporters.Exporter;
import core.schedulers.Scheduler;

import static core.events.EventNotifier.eventNotifier;

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
    private final int threshold;

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
        this.threshold = Integer.MAX_VALUE;
    }

    /**
     * Creates a new engine and assigns it the given exporter. The exporter already include the scheduler
     * that is gonna be used by the engine.
     *
     * @param exporter  exporter used to export routes.
     */
    public Engine(Exporter exporter, int threshold) {
        this.exporter = exporter;
        this.scheduler = exporter.getScheduler();
        this.threshold = threshold;
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

        eventNotifier().notifyStartEvent(new StartEvent(0, scheduler));

        // start the simulation by having the destination export its self route to its neighbors
        exporter.export(destination, topology.getPolicy());

        int time = 0;
        while (scheduler.hasMessages()) {

            Message message = scheduler.nextMessage();
            time = message.getArrivalTime();

            if (time >= threshold) {
                eventNotifier().notifyThresholdReachedEvent(new ThresholdReachedEvent(time, threshold));
                break;
            }

            message.getTarget().process(message, exporter);

            // check there is expired timers and if so export the routes associated with the
            // timers right now.
            // this MUST be called after processing each message!
            // calling this method might generate new messages to be added to the scheduler!
            exporter.export(scheduler.getExpiredTimers());

            if (!scheduler.hasMessages()) {
                // a terminate even is fired here to allow external components to add more messages if
                // necessary to the scheduler
                eventNotifier().notifyTerminateEvent(new TerminateEvent(time));
            }
        }

        eventNotifier().notifyEndEvent(new EndEvent(time));
    }

}
