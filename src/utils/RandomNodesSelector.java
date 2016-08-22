package utils;

import core.network.Network;
import core.network.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Randomly selects a subset of nodes from a core.network. Once a node is selected, in the next selection it can not be
 * selected again.
 */
public class RandomNodesSelector {

    private Network network;
    private List<Node> availableNodes;
    private Random random = new Random();

    public RandomNodesSelector(Network network) {
        this.network = network;
        reset();
    }

    public RandomNodesSelector(List<Node> nodes) {
        availableNodes = new ArrayList<>(nodes);
    }

    /**
     * Randomly selects nodeCount nodes from the set of nodes in the core.network associated with this selector.
     * Once this method is called, the returned nodes are no longer available in the next call. If the requested
     * number of nodes is higher than the number of nodes available, only the number of nodes available will be
     * returned.
     *
     * @param nodeCount number of nodes to select.
     * @return collection with the selected nodes
     */
    public Collection<Node> selectNodes(int nodeCount) {
        Collection<Node> selectedNodes = new ArrayList<>(nodeCount);

        int maxSelectableNodes = Math.min(nodeCount, availableNodes.size());
        for (int i = 0; i < maxSelectableNodes; i++) {
            int index = random.nextInt(availableNodes.size());
            selectedNodes.add(availableNodes.get(index));
            availableNodes.remove(index);
        }

        return selectedNodes;
    }

    /**
     * Resets all the nodes available.
     */
    public void reset() {
        // copy all nodes to an array list
        availableNodes = new ArrayList<>(network.getNodes());
    }

}
