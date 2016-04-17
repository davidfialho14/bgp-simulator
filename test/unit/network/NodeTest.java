package network;

import org.junit.Before;
import org.junit.Test;

import static network.Factory.createLink;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class NodeTest {

    Network network;    // network where the node is created
    Node node;

    @Before
    public void setUp() throws Exception {
        network = null;
        node = new Node(0);
    }

    @Test
    public void addOutLink_FromNode0ToNode1ToNodeWithoutOutLinks_NodeContainsOutNeighbourNode1() throws Exception {
        node.addOutLink(createLink(0, 1));

        assertThat(node.getOutLinks(), containsInAnyOrder(createLink(0, 1)));
    }

    @Test
    public void addInLink_FromNode0ToNode1ToNodeWithoutInLinks_NodeContainsInLinkFromNode0ToNode1() throws Exception {
        node.addInLink(createLink(0, 1));

        assertThat(node.getInLinks(), containsInAnyOrder(createLink(0, 1)));
    }
}