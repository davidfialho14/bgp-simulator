package network;

public interface Scheduler {

    /**
     * Returns the next exported route scheduled.
     * @return next exported route scheduled or null if there is no routes scheduled.
     */
    ExportedRoute get();

    /**
     * Schedules a new route. Adds it to the scheduler.
     * @param link link through which the route was exported.
     * @param route route to be scheduled.
     */
    void schedule(Link link, Route route);

}
