package network;

import dummies.DummyLabel;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static wrappers.DummyWrapper.dummyLink;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network network;

    @Before
    public void setUp() throws Exception {
        network = new Network();
    }

    @Test
    public void add_NodeWithId0_ContainsNodeWithId0() throws Exception {
        network.addNode(0);

        assertThat(network.getIds(), containsInAnyOrder(0));
    }

    @Test
    public void add_NodesWithIds0And1_ContainsNodesWithIds0And1() throws Exception {
        network.addNode(0); network.addNode(1);

        assertThat(network.getIds(), containsInAnyOrder(0, 1));
    }

    @Test
    public void add_NodeWithId0Twice_ThrowsNodeExistsException() throws Exception {
        network.addNode(0);

        thrown.expect(NodeExistsException.class);
        thrown.expectMessage("node with id '0' already exists");
        network.addNode(0);
    }

    @Test
    public void link_Node0ToNode1BothAlreadyAddedToTheNetwork_ContainsLinkBetweenNode0AndNode1() throws Exception {
        network.addNode(0); network.addNode(1);

        network.link(0, 1, new DummyLabel());

        assertThat(network.getLinks(), containsInAnyOrder(dummyLink(0, 1)));
    }

    @Test
    public void link_Node0ToNode1ButNode0WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, new DummyLabel());
    }

    @Test
    public void link_Node0ToNode1ButNode1WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(0);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '1' does not exist");
        network.link(0, 1, new DummyLabel());
    }

    @Test
    public void link_Node0ToNode1Twice_ContainsOnlyOneLink() throws Exception {
        network.addNode(0); network.addNode(1);

        network.link(0, 1, new DummyLabel());
        network.link(0, 1, new DummyLabel());

        assertThat(network.getLinks().size(), is(1));
    }

    @Test
    public void link_Node0ToNode0_ContainsLinkFromNode0ToNode0() throws Exception {
        network.addNode(0);

        network.link(0, 0, new DummyLabel());

        assertThat(network.getLinks(), containsInAnyOrder(dummyLink(0, 0)));
    }

}