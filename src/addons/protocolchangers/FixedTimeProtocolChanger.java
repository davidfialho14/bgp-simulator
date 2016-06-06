package addons.protocolchangers;

import simulation.Engine;
import simulation.State;
import simulation.TimeListener;

/**
 * Base class that all protocol changers based on a fixed time must extend. It implements a condition method
 * isTimeToChange() that indicates if it is time to change or not and should be used by subclasses to verify
 * if the protocol mut be changed.
 */
public abstract class FixedTimeProtocolChanger extends ProtocolChanger implements TimeListener {

    protected long changeTime;
    private boolean changed = false;

    /**
     * Creates a fixed time protocol changer by associating it with a time instant to change protocol.
     *
     * @param changeTime time to change protocol.
     */
    public FixedTimeProtocolChanger(long changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * Creates a fixed time protocol changer by assigning it an engine and a state and associating it with a
     * time instant to change protocol.
     *
     * @param engine engine to assign to.
     * @param state state to assign to.
     * @param changeTime time to change protocol.
     */
    public FixedTimeProtocolChanger(Engine engine, State state, long changeTime) {
        super(engine, state);
        this.changeTime = changeTime;
    }

    /**
     * Assigns the protocol changer tp an engine and state and registers the protocol changer to the time
     * property of the engine.
     *
     * @param engine engine to assign to.
     * @param state  state to assign to.
     */
    @Override
    public void assignTo(Engine engine, State state) {
        super.assignTo(engine, state);
        engine.timeProperty().addListener(this);
    }

    /**
     * Checks if it is time to change the protocol. It will only return true once in the first time the current
     * time is greater or equal to the change time.
     *
     * @param currentTime current time.
     * @return true if it is time to change or false otherwise.
     */
    protected boolean isTimeToChange(long currentTime) {
        if (!changed && currentTime >= changeTime) {
            changed = true;
            return true;
        }

        return false;
    }

    @Override
    public void onTimeChange(long newTime) {
        if (isTimeToChange(newTime)) {
            onTimeToChange();
        }
    }

    /**
     * Invoked only once when the time to change is reached.
     */
    public abstract void onTimeToChange();
}
