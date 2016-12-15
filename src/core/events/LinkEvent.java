package core.events;

import core.Link;

/**
 * A link event is a simulation event that is associated with a link. Examples o these are the
 * export and arrival events.
 */
public interface LinkEvent extends SimulationEvent {

    /**
     * Returns the link associated with the event.
     *
     * @return the link associated with the event.
     */
    Link getLink();

}
