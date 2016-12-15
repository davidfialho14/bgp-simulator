package utils;

import core.Link;
import core.events.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static core.events.EventNotifier.eventNotifier;

/**
 * The link FIFO order checker is a tool to automatically check if the FIFO order of the links is
 * being respected. It checks for this on the fly, which means that it will generate an error if
 * it detects that a message was sent and was not received in the same order.
 */
public class LinkFIFOOrderChecker implements ArrivalListener, ExportListener {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields defining the engine's state
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * The link logger logs all exports and arrivals in order. An exported message is logged once
     * it is sent through a link and it is logged when the messages arrives at the destination.
     */
    private static class LinkLogger {

        private final HashMap<Link, Queue<ArrivalEvent>> linksArrivals = new HashMap<>();
        private final HashMap<Link, Queue<ExportEvent>> linksExports = new HashMap<>();

        public Queue<ArrivalEvent> getArrivals(Link link) {
            return linksArrivals.get(link);
        }

        public Queue<ExportEvent> getExports(Link link) {
            return linksExports.get(link);
        }

        public void log(ArrivalEvent event) {
            log(linksArrivals, event);
        }

        public void log(ExportEvent event) {
            log(linksExports, event);
        }

        private <T extends LinkEvent> void log(HashMap<Link, Queue<T>> linksEvents, T event) {
            Queue<T> newEventQueue = new LinkedList<>();
            Queue<T> eventQueue = linksEvents.putIfAbsent(event.getLink(), newEventQueue);

            if (eventQueue == null) {
                eventQueue = newEventQueue;
            }

            eventQueue.add(event);
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final HashMap<Link, Queue<ExportEvent>> linkQueue = new HashMap<>();
    private LinkLogger linkLogger;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     * Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public LinkFIFOOrderChecker(boolean logEvents) {
        eventNotifier().addArrivalListener(this);
        eventNotifier().addExportListener(this);

        this.linkLogger = logEvents ? new LinkLogger() : null;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void unregister() {
        eventNotifier().removeArrivalListener(this);
        eventNotifier().removeExportListener(this);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     * Event methods used to implement the checker
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void onExported(ExportEvent event) {
        if (linkLogger != null) linkLogger.log(event);

        Queue<ExportEvent> newLinkQueue = new LinkedList<>();
        Queue<ExportEvent> oldExports = linkQueue.putIfAbsent(event.getLink(), newLinkQueue);

        if (oldExports == null) {
            oldExports = newLinkQueue;
        }

        oldExports.add(event);
    }

    @Override
    public void onArrival(ArrivalEvent event) {
        if (linkLogger != null) linkLogger.log(event);

        // get from the link queue the next export event the should be arriving now
        ExportEvent exportEvent = linkQueue.get(event.getLink()).poll();

        // check if the route of this next arrival event matches the route of the export event
        // that should be arriving now
        if (!event.getRoute().equals(exportEvent.getRoute())) {
            Link link = event.getLink();

            if (linkLogger == null) {
                new MessageOrderMissMatchException(link, event, exportEvent)
                        .print();
            } else {
                new MessageOrderMissMatchException(link, event, exportEvent,
                        linkLogger.getArrivals(link), linkLogger.getExports(link))
                        .print();
            }
        }
    }

}
