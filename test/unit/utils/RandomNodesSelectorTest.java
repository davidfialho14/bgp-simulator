package utils;

import core.topology.Node;
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
                Arrays.asList(new Node(0), new Node(1), new Node(2), new Node(3)));

        Collection<Node> selectedNodes = nodesSelector.selectNodes(0);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select2Nodes_From4AvailableNodes_Selected2Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new Node(0), new Node(1), new Node(2), new Node(3)));

        Collection<Node> selectedNodes = nodesSelector.selectNodes(2);

        assertThat(selectedNodes.size(), is(2));
    }

    @Test
    public void select1Node_AfterSelectingAll4AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new Node(0), new Node(1), new Node(2), new Node(3)));

        nodesSelector.selectNodes(4);
        Collection<Node> selectedNodes = nodesSelector.selectNodes(1);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select1Node_From0AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(new ArrayList<>());

        Collection<Node> selectedNodes = nodesSelector.selectNodes(1);

        assertThat(selectedNodes.size(), is(0));
    }

    @Test
    public void select2Node_From1AvailableNode_Selected1Node() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(Collections.singletonList(new Node(0)));

        Collection<Node> selectedNodes = nodesSelector.selectNodes(2);

        assertThat(selectedNodes.size(), is(1));
    }

}