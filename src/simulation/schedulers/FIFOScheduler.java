package simulation.schedulers;

public class FIFOScheduler extends AbstractScheduler {

    @Override
    protected long schedule(ScheduledRoute scheduledRoute) {
        return scheduledRoute.getTimestamp() + 1;
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
}
