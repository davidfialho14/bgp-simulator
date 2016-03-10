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
        network = new Network(ComponentFactory.createProtocolFactory());
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

        Link[] expectedLinks = { ComponentFactory.createLink(network, 0, 1) };
        network.link(0, 1, ComponentFactory.createLabel());

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkExistingSourceToNonExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, ComponentFactory.createLabel());
    }

    @Test
    public void linkNonExistingSourceToExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, ComponentFactory.createLabel());
    }

    @Test
    public void linkSameNodesTwice_NetworkContainsBothLinks() throws Exception {
        network.addNode(0); network.addNode(1);
        network.link(0, 1, ComponentFactory.createLabel());  // first link

        Link[] expectedLinks = { ComponentFactory.createLink(network, 0, 1), ComponentFactory.createLink(network, 0, 1) };
        network.link(0, 1, ComponentFactory.createLabel());  // same link twice

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

}