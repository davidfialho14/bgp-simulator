package addons.statsmanagers;

import addons.protocolchangers.FixedTimeProtocolChanger;
import network.Node;
import protocols.D1R1Protocol;
import simulation.Engine;
import simulation.State;
import simulation.events.DetectEvent;
import simulation.events.DetectListener;
import simulation.events.ImportEvent;
import simulation.events.ImportListener;

import java.util.HashSet;
import java.util.Set;

/**
 * All nodes start with no ability to detect oscillations. After one fixed moment of time T all nodes start to detect
 * oscillations. After this moment the manager counts:
 *  - the number os messages exchanged until the protocol reaches a stable state
 *  - number of links that were cut-off
 *  - number of nodes that have cut-off links
 */
public class StatsManager extends FixedTimeProtocolChanger implements ImportListener, DetectListener {

    private int messageCount = 0;                      // message count after changing all protocols
    private int cutOffLinkCount = 0;                    // number of cut-off links
    private Set<Node> detectingNodes = new HashSet<>(); // stores all the different detecting nodes

    private boolean protocolChanged = false;    // indicates if the protocols have been changed

    /**
     * Creates a stats manager.
     */
    public StatsManager(Engine engine, State state, long timeToChange) {
        super(engine, state, timeToChange);
    }

    /**
     * Returns the count of exchanged messages after the protocols have been changed.
     *
     * @return count of exchanged messages after the protocols have been changed.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * Returns the number of links the were cut-off.
     *
     * @return number of links the were cut-off.
     */
    public int getCutOffLinkCount() {
        return cutOffLinkCount;
    }

    /**
     * Returns number of nodes that have cut-off links.
     *
     * @return number of nodes that have cut-off links.
     */
    public int getDetectingNodesCount() {
        return detectingNodes.size();
    }

    @Override
    public void onTimeChange(long newTime) {
        if (isTimeToChange(newTime)) {
            changeAllProtocols(new D1R1Protocol());
            protocolChanged = true;
        }
    }

    /**
     * Called every time a new message is received by any node.
     *
     * @param event import event that occurred.
     */
    @Override
    public void onImported(ImportEvent event) {
        if (protocolChanged) {
            messageCount++;
        }
    }

    /**
     * Invoked when a detect event occurs.
     *
     * @param event detect event that occurred.
     */
    @Override
    public void onDetected(DetectEvent event) {
        detectingNodes.add(event.getDetectingNode());
        cutOffLinkCount++;
    }

}
