package utils;

import core.Link;
import core.events.DetectEvent;
import core.events.DetectListener;

import java.util.HashSet;
import java.util.Set;

import static core.events.EventNotifier.eventNotifier;

public class TurnedOffLinksMonitor implements DetectListener {

    Set<Link> turnedOffLinks = new HashSet<>();

    public TurnedOffLinksMonitor() {
        eventNotifier().addDetectListener(this);
    }

    public void unregister() {
        eventNotifier().removeDetectListener(this);
    }

    @Override
    public void onDetected(DetectEvent event) {
        Link link = event.getOutLink();

        if (!turnedOffLinks.add(link)) {
            System.out.println("Link " + link + " was already turned off!");
        }
    }
}
