package simulators.gradualdeployment;

import core.topology.ConnectedNode;
import simulators.Dataset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stores the data collected during a gradual deployment simulation. Stores the set of nodes that have deployed a new
 * protocols during one simulation instance.
 */
public class GradualDeploymentDataset implements Dataset {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Private Fields
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private final List<ConnectedNode> deployingNodes = new ArrayList<>();

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to access the stored data specific to gradual deployment
     *  simulations
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Returns a collection with all deploying nodes.
     *
     * @return a collection with all deploying nodes.
     */
    public Collection<ConnectedNode> getDeployingNodes() {
        return deployingNodes;
    }

    /**
     * Returns the number of deploying nodes.
     *
     * @return the number of deploying nodes.
     */
    public int getDeployingNodesCount() {
        return this.deployingNodes.size();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface - Methods to update the data
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Sets a node as a deploying node.
     *
     * @param node node to set as deploying node.
     */
    public void setAsDeployingNode(ConnectedNode node) {
        deployingNodes.add(node);
    }

    /**
     * Clears all data from the dataset.
     */
    public void clear() {
        deployingNodes.clear();
    }

}
