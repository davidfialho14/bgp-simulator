package simulation.schedulers;

import network.Link;

import java.util.HashMap;
import java.util.Map;

public class RandomScheduler extends AbstractScheduler {

    public static final int MAX_DELAY = 10;    // defines the maximum delay

    private RandomDelayGenerator randomDelayGenerator = new RandomDelayGenerator(0, MAX_DELAY);
    private Map<Link, Long> lastTimes = new HashMap<>();   // stores the last times used for each link

    /**
     * Returns the last time associated with the given link.
     * @param link link to get the last time of.
     * @return last time of the link or null if the link does not exist.
     */
    public Long getLastTime(Link link) {
        return lastTimes.get(link);
    }

    @Override
    protected long schedule(ScheduledRoute scheduledRoute) {
        long scheduledTime = scheduledRoute.getTimestamp() + randomDelayGenerator.nextDelay();

        Long lastTime = lastTimes.get(scheduledRoute.getLink());
        if (lastTime != null) {
            scheduledTime = Long.max(scheduledTime, lastTime + 1);
        }

        lastTimes.put(scheduledRoute.getLink(), scheduledTime); // update the link last time

        return scheduledTime;
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
