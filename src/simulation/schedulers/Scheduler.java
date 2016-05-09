package simulation.schedulers;

import network.Link;

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

    /**
     * Removes all the routes being exported through the given link.
     *
     * @param link link exporting the routes to be removed.
     */
    void removeRoutes(Link link);

    /**
     * Returns the current time of the scheduler.
     *
     * @return the current time of the scheduler.
     */
    long getCurrentTime();

    /**
     * Resets the scheduler.
     */
    void reset();
}
