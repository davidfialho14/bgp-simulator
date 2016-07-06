package utils;

import network.Node;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

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

        System.out.println(selectedNodes);
        assertThat(selectedNodes.size(), is(2));
    }

    @Test
    public void select1Node_AfterSelectingAll4AvailableNodes_Selected0Nodes() throws Exception {

        RandomNodesSelector nodesSelector = new RandomNodesSelector(
                Arrays.asList(new Node(0), new Node(1), new Node(2), new Node(3)));

        nodesSelector.selectNodes(4);
        Collection<Node> selectedNodes = nodesSelector.selectNodes(1);

        System.out.println(selectedNodes);
        assertThat(selectedNodes.size(), is(0));
    }

}