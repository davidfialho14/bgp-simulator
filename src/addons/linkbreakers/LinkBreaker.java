package addons.linkbreakers;

import core.topology.Link;
import core.Engine;
import core.State;

/**
 * A link breaker is assigned to a an engine and a state and will break links of the state's core.topology depending on the
 * information provided by the engine and the state.
 */
public abstract class LinkBreaker {

    private Engine engine;  // assigned engine
    private State state;    // assigned state

    /**
     * Creates a link breaker not assigned to any engine and state.
     */
    public LinkBreaker() {
        this.engine = null;
        this.state = null;
    }

    /**
     * Creates a link breaker assigned to an engine and state.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     */
    public LinkBreaker(Engine engine, State state) {
        assignTo(engine, state);
    }

    /**
     * Assigns the link breaker to an engine and state. If the link breaker is already assigned it the new assignment
     * overrides the previous.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     */
    public void assignTo(Engine engine, State state) {
        this.engine = engine;
        this.state = state;
    }

    /**
     * Breaks a link. This method can be used by any link breaker implementation to break a link.
     *
     * @param link link to break.
     */
    protected void breakLink(Link link) {
        state.getNetwork().removeLink(link);
        engine.onBrokenLink(link);
        state.get(link.getSource()).getTable().removeOutLink(link);
    }

}
