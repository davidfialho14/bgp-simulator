package utils;

import core.topology.ConnectedNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class RandomNodesSelectorTest {

    @Test
    public void select0Nodes_From4AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new ConnectedNode(0), new ConnectedNode(1), new ConnectedNode(2), new ConnectedNode(3)));

        Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(0);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select2Nodes_From4AvailableNodes_Selected2Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new ConnectedNode(0), new ConnectedNode(1), new ConnectedNode(2), new ConnectedNode(3)));

        Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(2);

        assertThat(selectedNodes.size(), is(2));
    }

    @Test
    public void select1Node_AfterSelectingAll4AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new ConnectedNode(0), new ConnectedNode(1), new ConnectedNode(2), new ConnectedNode(3)));

        nodesSelector.selectNodes(4);
        Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(1);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select1Node_From0AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(new ArrayList<>());

        Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(1);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select2Node_From1AvailableNode_Selected1Node() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(Collections.singletonList(new ConnectedNode(0)));

        Collection<ConnectedNode> selectedNodes = nodesSelector.selectNodes(2);

        assertThat(selectedNodes.size(), is(1));
    }

}