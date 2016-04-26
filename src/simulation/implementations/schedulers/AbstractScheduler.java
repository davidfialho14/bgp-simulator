package simulation.implementations.schedulers;

import network.Link;
import simulation.ScheduledRoute;
import simulation.Scheduler;

import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class AbstractScheduler implements Scheduler {

    /*
        Defines the order in which the routes will be scheduled based on the timestamps associated
        with the routes.
     */
    private PriorityQueue<ScheduledRoute> queue = new PriorityQueue<>();
    private long currentTime = 0L;

    /**
     * Returns the next scheduled route.
     *
     * @return next route scheduled or null if there is no routes scheduled.
     */
    public ScheduledRoute get() {
        ScheduledRoute nextScheduledRoute = queue.poll();
        if (nextScheduledRoute != null)
            currentTime = nextScheduledRoute.getTimestamp();

        return nextScheduledRoute;
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
        return currentTime;
    }
}
