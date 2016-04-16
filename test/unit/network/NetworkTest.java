package network;

import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network();
    }

    @Test
    public void addAnyNode_NetworkContainsAddedNode() throws Exception {
        Integer[] expectedIds = {0};
        network.addNode(0);

        assertThat(network.getIds(), containsInAnyOrder(expectedIds));
    }

    @Test
    public void addAny2Nodes_NetworkContainsThe2Nodes() throws Exception {
        Integer[] expectedIds = {0, 1};
        network.addNode(0); network.addNode(1);

        assertThat(network.getIds(), containsInAnyOrder(expectedIds));
    }

    @Test
    public void addTheSameNodeTwice_NetworkContainsAddedNode() throws Exception {
        network.addNode(0);

        thrown.expect(NodeExistsException.class);
        thrown.expectMessage("node with id '0' already exists");
        network.addNode(0);
    }

    /**
     * Links two nodes with the given ids in the network object under test. The other link attributes are
     * initialized to null.
     * @param srdId id of the source node.
     * @param destId id of the destination node.
     * @throws NodeNotFoundException
     */
    private void linkNodes(int srdId, int destId) throws NodeNotFoundException {
        network.link(srdId, destId, null);
    }

    @Test
    public void link2ExistingNodes_NetworkContainsLinkBetweenThe2Nodes() throws Exception {
        network.addNode(0); network.addNode(1);

        Link[] expectedLinks = { Factory.createLink(0, 1) };
        linkNodes(0, 1);

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkExistingSourceToNonExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        linkNodes(0, 1);
    }

    @Test
    public void linkNonExistingSourceToExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        linkNodes(0, 1);
    }

    @Test
    public void linkSameNodesTwice_NetworkContainsBothLinks() throws Exception {
        network.addNode(0); network.addNode(1);
        linkNodes(0, 1);  // first link

        Link[] expectedLinks = { Factory.createLink(0, 1), Factory.createLink(0, 1) };
        linkNodes(0, 1);  // same link twice

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkWithSourceEqualToDestination_NetworkContainsLink() throws Exception {
        network.addNode(0);

        Link[] expectedLinks = { Factory.createLink(0, 0) };
        linkNodes(0, 0);

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

}