package addons.linkbreakers;

import core.network.Link;
import core.Engine;
import core.State;
import core.TimeListener;

/**
 * A fixed breaker will break a given link in a fixed instant of time T.
 */
public class FixedTimeLinkBreaker extends LinkBreaker implements TimeListener {

    private Link linkToBreak;
    private long timeToBreak;
    private boolean broken = false;    // indicates that the link was already broken

    /**
     * Constructs a fixed link breaker with the link to break and the instant of time to do it.
     *
     * @param link link to be broken.
     * @param timeToBreak instant of time to break the link.
     */
    public FixedTimeLinkBreaker(Link link, long timeToBreak) {
        this.linkToBreak = link;
        this.timeToBreak = timeToBreak;
    }

    /**
     * Assigns the link breaker to an engine and state. If the link breaker is already assigned it the new assignment
     * overrides the previous.
     *
     * @param engine engine to assign to.
     * @param state  state to assign to.
     */
    @Override
    public void assignTo(Engine engine, State state) {
        super.assignTo(engine, state);
        engine.timeProperty().addListener(this);
    }

    @Override
    public void onTimeChange(long newTime) {
        if (!broken && newTime >= timeToBreak) {
            breakLink(linkToBreak);
            broken = true;
        }
    }
}
