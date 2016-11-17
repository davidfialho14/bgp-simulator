package core.schedulers;

import core.topology.Link;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class FIFOScheduler implements Scheduler {

    private final Queue<ScheduledRoute> scheduleQueue = new ArrayDeque<>();

    @Override
    public ScheduledRoute get() {
        return scheduleQueue.poll();
    }

    @Override
    public void put(ScheduledRoute scheduledRoute) {
        scheduleQueue.offer(scheduledRoute);
    }

    @Override
    public boolean hasRoute() {
        return scheduleQueue.peek() != null;
    }

    @Override
    public void removeRoutes(Link link) {
        Iterator<ScheduledRoute> iterator = scheduleQueue.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getLink().equals(link)) {
                iterator.remove();
            }
        }
    }

    @Override
    public long getCurrentTime() {
        ScheduledRoute nextScheduledRoute = scheduleQueue.peek();
        if (nextScheduledRoute == null) {
            return Long.MAX_VALUE;
        } else {
            return nextScheduledRoute.getTimestamp();
        }
    }

    /**
     * Sets the minimum message delay. (implementation is optional)
     *
     * @param delay delay to set as minimum.
     */
    @Override
    public void setMinDelay(int delay) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the maximum message delay. (implementation is optional)
     *
     * @param delay delay to set as maximum.
     */
    @Override
    public void setMaxDelay(int delay) {
        throw new UnsupportedOperationException();
    }


    /**
     * Resets the scheduler.
     */
    @Override
    public void reset() {
        scheduleQueue.clear();
    }

}
