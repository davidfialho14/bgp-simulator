package network;

import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import protocols.DummyProtocolFactory;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network(new DummyProtocolFactory(), null, null);
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

    @Test
    public void link2ExistingNodes_NetworkContainsLinkBetweenThe2Nodes() throws Exception {
        network.addNode(0); network.addNode(1);

        Link[] expectedLinks = { Factory.createLink(network, 0, 1) };
        network.link(0, 1, null);

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkExistingSourceToNonExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, null);
    }

    @Test
    public void linkNonExistingSourceToExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, null);
    }

    @Test
    public void linkSameNodesTwice_NetworkContainsBothLinks() throws Exception {
        network.addNode(0); network.addNode(1);
        network.link(0, 1, null);  // first link

        Link[] expectedLinks = { Factory.createLink(network, 0, 1), Factory.createLink(network, 0, 1) };
        network.link(0, 1, null);  // same link twice

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkWithSourceEqualToDestination_NetworkContainsLink() throws Exception {
        network.addNode(0);

        Link[] expectedLinks = { Factory.createLink(network, 0, 0) };
        network.link(0, 0, null);

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

}