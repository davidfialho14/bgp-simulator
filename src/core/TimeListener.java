package core;

import core.events.SimulationEventListener;

@FunctionalInterface
public interface TimeListener extends SimulationEventListener {

    void onTimeChange(long newTime);
}
