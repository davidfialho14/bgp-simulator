package core.schedulers;

import core.topology.Link;

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
     * Checks if the scheduler has one more route.
     *
     * @return true if the scheduler has at least a route or false otherwise.
     */
    boolean hasRoute();

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

    /**
     * Sets the minimum message delay. (implementation is optional)
     *
     * @param delay delay to set as minimum.
     */
    void setMinDelay(int delay);

    /**
     * Sets the maximum message delay. (implementation is optional)
     *
     * @param delay delay to set as maximum.
     */
    void setMaxDelay(int delay);
}
