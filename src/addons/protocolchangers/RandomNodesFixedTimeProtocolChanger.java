package addons.protocolchangers;

import network.Node;
import protocols.Protocol;
import simulation.Engine;
import simulation.State;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Fixed time protocol changer based on a random set of nodes that are changed when it is time to change the protocol.
 */
public class RandomNodesFixedTimeProtocolChanger extends FixedTimeProtocolChanger {

    private final Protocol protocolToChangeTo;
    private final Set<Node> nodeSet = new HashSet<>();

    private RandomNodesFixedTimeProtocolChanger(Builder builder) {
        super(builder.engine, builder.state, builder.changeTime);
        this.protocolToChangeTo = builder.protocol;
        buildNodeSet(builder.minNodes, builder.maxNodes);
    }

    public static class Builder {

        private final Engine engine;
        private final State state;
        private final long changeTime;
        private final Protocol protocol;

        private int minNodes = 1;
        private int maxNodes = 1;

        public Builder(Engine engine, State state, long changeTime, Protocol protocol) {
            this.engine = engine;
            this.state = state;
            this.changeTime = changeTime;
            this.protocol = protocol;
        }

        public Builder minNodes(int min) {
            this.minNodes = min;
            return this;
        }

        public Builder maxNodes(int max) {
            this.maxNodes = max;
            return this;
        }

        public RandomNodesFixedTimeProtocolChanger build() {
            return new RandomNodesFixedTimeProtocolChanger(this);
        }
    }

    /**
     * Invoked only once when the time to change is reached.
     */
    @Override
    public void onTimeToChange() {
        nodeSet.forEach(node -> changeProtocol(node, protocolToChangeTo));
    }

    protected void buildNodeSet(int minNodes, int maxNodes) {
        Random random = new Random();
        int maxNodeId = state.getNetwork().getNodeCount();
        int nodeCount = Math.min(maxNodes - minNodes + minNodes, maxNodeId);

        while (nodeCount > 0) {
            int nodeId = random.nextInt(maxNodeId);

            // ensure nodes are not repeated
            if (nodeSet.add(state.getNetwork().getNode(nodeId))) {
                nodeCount--;
            }
        }
    }
}
