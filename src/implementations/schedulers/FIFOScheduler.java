package implementations.schedulers;

import network.ScheduledRoute;

public class FIFOScheduler extends AbstractScheduler {

    @Override
    protected long schedule(ScheduledRoute scheduledRoute) {
        return scheduledRoute.getTimestamp() + 1;
    }

}
