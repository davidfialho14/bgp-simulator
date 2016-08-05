package addons.protocolchangers;

import protocols.Protocol;
import core.Engine;
import core.State;

public class ChangeAllFixedTimeProtocolChanger extends FixedTimeProtocolChanger {
    private Protocol protocol;

    /**
     * Creates a fixed time protocol changer by assigning it an engine and a state and associating it with a
     * time instant to change protocol.
     *
     * @param engine     engine to assign to.
     * @param state      state to assign to.
     * @param changeTime time to change protocol.
     * @param protocol   protocol to change to
     */
    public ChangeAllFixedTimeProtocolChanger(Engine engine, State state, long changeTime, Protocol protocol) {
        super(engine, state, changeTime);
        this.protocol = protocol;
    }

    /**
     * Invoked only once when the time to change is reached.
     */
    @Override
    public void onTimeToChange() {
        changeAllProtocols(protocol);
    }

}
