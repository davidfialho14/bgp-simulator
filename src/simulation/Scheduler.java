package simulation;

public interface Scheduler {

    /**
     * Returns the next scheduled route.
     * @return next route scheduled or null if there is no routes scheduled.
     */
    ScheduledRoute get();

    /**
     * Adds a new route to the scheduler.
     * @param scheduledRoute route to be added to the scheduler.
     */
    void put(ScheduledRoute scheduledRoute);

}
