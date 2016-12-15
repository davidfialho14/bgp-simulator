package utils;

import core.Link;
import core.events.ArrivalEvent;
import core.events.ExportEvent;

import java.util.Queue;

/**
 * Thrown when the LinkFIFOOrderChecker finds that the FIFO order is not match for a given link.
 */
public class MessageOrderMissMatchException extends Exception {

    private final Link link;
    private final ArrivalEvent arrivalMissMatch;
    private final ExportEvent exportMissMatch;
    private final Queue<ArrivalEvent> arrivalEvents;
    private final Queue<ExportEvent> exportEvents;

    public MessageOrderMissMatchException(Link link, ArrivalEvent arrivalMissMatch,
                                          ExportEvent exportMissMatch,
                                          Queue<ArrivalEvent> arrivalEvents,
                                          Queue<ExportEvent> exportEvents) {
        this.link = link;
        this.arrivalMissMatch = arrivalMissMatch;
        this.exportMissMatch = exportMissMatch;
        this.arrivalEvents = arrivalEvents;
        this.exportEvents = exportEvents;
    }

    public MessageOrderMissMatchException(Link link, ArrivalEvent arrivalMissMatch,
                                          ExportEvent exportMissMatch) {
        this.link = link;
        this.arrivalMissMatch = arrivalMissMatch;
        this.exportMissMatch = exportMissMatch;
        this.arrivalEvents = null;
        this.exportEvents = null;
    }

    public Link getLink() {
        return link;
    }

    public ArrivalEvent getArrivalMissMatch() {
        return arrivalMissMatch;
    }

    public ExportEvent getExportMissMatch() {
        return exportMissMatch;
    }

    public boolean hasLogs() {
        return arrivalEvents != null;
    }

    public Queue<ArrivalEvent> getArrivalEvents() {
        return arrivalEvents;
    }

    public Queue<ExportEvent> getExportEvents() {
        return exportEvents;
    }

    public void print() {
        System.out.println("Order in link %s is incorrect " + link);
        System.out.println("Miss match:");
        System.out.println("\tArrives " + arrivalMissMatch);
        System.out.println("\tExpected " + exportMissMatch);

        if (hasLogs()) {
            System.out.println("Link logs:");
            System.out.println("Exports: " + exportEvents);
            System.out.println("Arrivals: " + arrivalEvents);
        }
    }
}
