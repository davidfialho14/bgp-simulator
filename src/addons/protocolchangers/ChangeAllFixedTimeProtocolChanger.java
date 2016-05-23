package addons.protocolchangers;

import protocols.Protocol;
import simulation.Engine;
import simulation.State;

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

    @Override
    public void onTimeChange(long newTime) {
        if (isTimeToChange(newTime)) {
            changeAllProtocols(protocol);
        }
    }
}
