package implementations.schedulers;

import network.ExportedRoute;
import network.Link;
import network.Route;
import network.Scheduler;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOScheduler implements Scheduler {

    private Queue<ExportedRoute> queue = new LinkedList<>();

    @Override
    public ExportedRoute get() {
        return queue.poll();
    }

    @Override
    public void schedule(Link link, Route route) {
        queue.offer(new ExportedRoute(link, route));
    }

}
