package utils;

import core.Destination;
import core.Link;
import core.Route;
import core.events.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static core.events.EventNotifier.eventNotifier;

public class CheckFIFOOrder implements ArrivalListener, ExportListener {

    private final HashMap<Link, Queue<ArrivalEvent>> importEvents = new HashMap<>();
    private final HashMap<Link, Queue<ExportEvent>> exportEvents = new HashMap<>();
    private final Destination destination;

    public CheckFIFOOrder(Destination destination) {
        this.destination = destination;
        eventNotifier().addArrivalListener(this);
        eventNotifier().addExportListener(this);
    }

    public void unregister() {
        eventNotifier().removeArrivalListener(this);
        eventNotifier().removeExportListener(this);
    }

    @Override
    public void onArrival(ArrivalEvent event) {
        Queue<ArrivalEvent> newImports = new LinkedList<>();
        Queue<ArrivalEvent> oldImports = importEvents.putIfAbsent(event.getLink(), newImports);

        if (oldImports == null) {
            oldImports = newImports;
        }

        oldImports.add(event);

    }

    @Override
    public void onExported(ExportEvent event) {
        Queue<ExportEvent> newExports = new LinkedList<>();
        Queue<ExportEvent> oldExports = exportEvents.putIfAbsent(event.getLink(), newExports);

        if (oldExports == null) {
            oldExports = newExports;
        }

        oldExports.add(event);
    }

    public boolean check() {

        for (Link link : importEvents.keySet()) {
            Queue<ArrivalEvent> importEventQueue = importEvents.get(link);
            Queue<ExportEvent> exportEventQueue = exportEvents.get(link);

            ArrivalEvent importEvent = importEventQueue.poll();
            ExportEvent exportEvent = exportEventQueue.poll();

            int counter = 1;
            while (importEvent != null && exportEvent != null) {
                Route importRoute = importEvent.getRoute();
                Route exportRoute = exportEvent.getRoute();

                if (importRoute != exportRoute) {
                    System.out.println(importEventQueue);
                    System.out.println(exportEventQueue);
                    System.out.println(counter);
                    System.out.println("import: " + importEvent);
                    System.out.println("export: " + exportEvent);
                    return false;
                }

                importEvent = importEventQueue.poll();
                exportEvent = exportEventQueue.poll();
                counter++;
            }


//            if (importEvent != null || exportEvent != null) {
//                System.out.println("queues are not both empty");
//                return false;
//            }
        }

        return true;
    }

}
