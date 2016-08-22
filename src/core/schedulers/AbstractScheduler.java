package core.schedulers;

import core.topology.Link;

import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class AbstractScheduler implements Scheduler {

    /*
        Defines the order in which the routes will be scheduled based on the timestamps associated
        with the routes.
     */
    private PriorityQueue<ScheduledRoute> queue = new PriorityQueue<>();

    /**
     * Returns the next scheduled route.
     *
     * @return next route scheduled or null if there is no routes scheduled.
     */
    public ScheduledRoute get() {
        return queue.poll();
    }

    /**
     * Adds a new route to the scheduler.
     *
     * @param scheduledRoute route to be added to the scheduler.
     */
    public void put(ScheduledRoute scheduledRoute) {
        scheduledRoute.setTimestamp(schedule(scheduledRoute));
        queue.offer(scheduledRoute);
    }

    /**
     * Checks if the scheduler has one more route.
     *
     * @return true if the scheduler has at least a route or false otherwise.
     */
    @Override
    public boolean hasRoute() {
        return !queue.isEmpty();
    }

    abstract protected long schedule(ScheduledRoute scheduledRoute);

    /**
     * Removes all the routes being exported through the given link.
     *
     * @param link link exporting the routes to be removed.
     */
    @Override
    public void removeRoutes(Link link) {
        Iterator<ScheduledRoute> iterator = queue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getLink().equals(link)) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns the current time of the scheduler.
     *
     * @return the current time of the scheduler.
     */
    @Override
    public long getCurrentTime() {
        ScheduledRoute nextScheduledRoute = queue.peek();
        if (nextScheduledRoute == null) {
            return Long.MAX_VALUE;
        } else {
            return nextScheduledRoute.getTimestamp();
        }
    }

    /**
     * Resets the scheduler.
     */
    @Override
    public void reset() {
        queue.clear();
    }
}
