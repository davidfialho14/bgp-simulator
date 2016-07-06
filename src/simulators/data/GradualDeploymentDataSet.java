package simulators.data;

import network.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores the set of nodes that have been deployed in each simulation.
 */
public class GradualDeploymentDataSet implements DataSet {

    private List<Node> deployedNodes = new ArrayList<>();

    /**
     * Returns the collection of deployed nodes.
     *
     * @return the collection of deployed nodes.
     */
    public Collection<Node> getDeployedNodes() {
        return deployedNodes;
    }

    /**
     * Returns the number of nodes that have been deployed.
     *
     * @return the number of nodes that have been deployed.
     */
    public int getDeployedNodesCount() {
        return this.deployedNodes.size();
    }

    /**
     * Adds a new node to the deployed collection.
     *
     * @param node deployed node to be added.
     */
    public void addDeployedNode(Node node) {
        deployedNodes.add(node);
    }

    /**
     * Clears all data from the dataset.
     */
    @Override
    public void clear() {
        deployedNodes.clear();
    }
}
