package core.schedulers;

import network.Link;

import java.util.HashMap;
import java.util.Map;

public class RandomScheduler extends AbstractScheduler {

    private RandomDelayGenerator randomDelayGenerator;
    private Map<Link, Long> lastTimes = new HashMap<>();   // stores the last times used for each link

    /**
     * Constructs a RandomScheduler by assigning it a minimum and maximum delay for the messages.
     *
     * @param minDelay minimum message delay.
     * @param maxDelay maximum message delay.
     */
    public RandomScheduler(int minDelay, int maxDelay) {
        this.randomDelayGenerator = new RandomDelayGenerator(minDelay, maxDelay);
    }

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

    /**
     * Sets the minimum message delay. (implementation is optional)
     *
     * @param delay delay to set as minimum.
     */
    @Override
    public void setMinDelay(int delay) {
        randomDelayGenerator.setMin(delay);
    }

    /**
     * Sets the maximum message delay. (implementation is optional)
     *
     * @param delay delay to set as maximum.
     */
    @Override
    public void setMaxDelay(int delay) {
        randomDelayGenerator.setMax(delay);
    }
}
