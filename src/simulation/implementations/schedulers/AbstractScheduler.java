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
    PriorityQueue<ScheduledRoute> queue = new PriorityQueue<>();

    /**
     * Returns the next scheduled route.
     * @return next route scheduled or null if there is no routes scheduled.
     */
    public ScheduledRoute get() {
        return queue.poll();
    }

    /**
     * Adds a new route to the scheduler.
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
}
