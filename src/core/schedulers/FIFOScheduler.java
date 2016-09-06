package core.schedulers;

import core.topology.Link;

import java.util.HashMap;
import java.util.Map;

public class FIFOScheduler extends AbstractScheduler {

    private Map<Link, Long> lastTimes = new HashMap<>();   // stores the last times used for each link

    @Override
    protected long schedule(ScheduledRoute scheduledRoute) {
        long scheduledTime = scheduledRoute.getTimestamp() + 1;

        Long lastTime = lastTimes.get(scheduledRoute.getLink());
        if (lastTime != null) {
            scheduledTime = Long.max(scheduledTime, lastTime + 1);
        }

        lastTimes.put(scheduledRoute.getLink(), scheduledTime); // update the link last time

        return scheduledTime;
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
        super.reset();
        lastTimes.clear();
    }

}
