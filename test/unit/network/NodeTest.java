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
        Link outLink = createLink(0, 1);

        node.addOutLink(outLink);

        Node expectedOutNeighbour = new Node(1);
        assertThat(node.getOutNeighbours(), containsInAnyOrder(expectedOutNeighbour));
    }

    @Test
    public void addInLink_FromNode0ToNode1ToNodeWithoutInLinks_NodeContainsInLinkFromNode0ToNode1() throws Exception {
        Link inLink = createLink(0, 1);

        node.addInLink(inLink);

        assertThat(node.getInLinks(), containsInAnyOrder(inLink));
    }
}