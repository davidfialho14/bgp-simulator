package addons.statsmanagers;

import addons.protocolchangers.FixedTimeProtocolChanger;
import protocols.D1R1Protocol;
import simulation.Engine;
import simulation.State;

/**
 * All nodes start with no ability to detect oscillations. After one fixed moment of time T all nodes start to detect
 * oscillations. After this moment the manager counts:
 *  - the number os messages exchanged until the protocol reaches a stable state
 *  - number of links that were cut-off
 *  - number of nodes that have cut-off links
 */
public class StatsManager extends FixedTimeProtocolChanger {

    private long messageCount = 0;      // message count after changing all protocols
    private long cutOffLinkCount = 0;   // number of cut-off links after changing protocols
    private long cutOffNodeCount = 0;   // number of nodes that have cut-off link after changing protocols

    /**
     * Creates a stats manager.
     */
    public StatsManager(Engine engine, State state, long timeToChange) {
        super(engine, state, timeToChange);
    }

    @Override
    public void onTimeChange(long newTime) {
        if (isTimeToChange(newTime)) {
            changeAllProtocols(new D1R1Protocol());
        }
    }

}
