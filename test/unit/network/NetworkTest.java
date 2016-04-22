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
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.ToNodeElement.to;
import static wrappers.network.NetworkWrapper.network;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network net;

    @Before
    public void setUp() throws Exception {
        net = new Network();
    }

    @Test
    public void add_NodeWithId0_ContainsNodeWithId0() throws Exception {
        net.addNode(0);

        assertThat(net.getIds(), containsInAnyOrder(0));
    }

    @Test
    public void add_NodesWithIds0And1_ContainsNodesWithIds0And1() throws Exception {
        net.addNode(0); net.addNode(1);

        assertThat(net.getIds(), containsInAnyOrder(0, 1));
    }

    @Test
    public void add_NodeWithId0Twice_ThrowsNodeExistsException() throws Exception {
        net.addNode(0);

        thrown.expect(NodeExistsException.class);
        thrown.expectMessage("node with id '0' already exists");
        net.addNode(0);
    }

    @Test
    public void link_Node0ToNode1BothAlreadyAddedToTheNetwork_ContainsLinkBetweenNode0AndNode1() throws Exception {
        net.addNode(0); net.addNode(1);

        net.link(0, 1, new DummyLabel());

        assertThat(net.getLinks(), containsInAnyOrder(dummyLink(0, 1)));
    }

    @Test
    public void link_Node0ToNode1ButNode0WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        net.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        net.link(0, 1, new DummyLabel());
    }

    @Test
    public void link_Node0ToNode1ButNode1WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        net.addNode(0);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '1' does not exist");
        net.link(0, 1, new DummyLabel());
    }

    @Test
    public void link_Node0ToNode1Twice_ContainsOnlyOneLink() throws Exception {
        net.addNode(0); net.addNode(1);

        net.link(0, 1, new DummyLabel());
        net.link(0, 1, new DummyLabel());

        assertThat(net.getLinks().size(), is(1));
    }

    @Test
    public void link_Node0ToNode0_ContainsLinkFromNode0ToNode0() throws Exception {
        net.addNode(0);

        net.link(0, 0, new DummyLabel());

        assertThat(net.getLinks(), containsInAnyOrder(dummyLink(0, 0)));
    }

    @Test
    public void remove_LinkFromNode0ToNode1FromEmptyNetwork_ReturnsFalse() throws Exception {
        assertThat(net.remove(dummyLink(0, 1)), is(false));
    }

    @Test
    public void remove_LinkFromNode0ToNode1FromNetworkWithLinkFromNode0ToNode1_NetworkHasNoLinks() throws Exception {
        net = network(
                link(from(0), to(1), new DummyLabel())
        );

        net.remove(dummyLink(0, 1));

        assertThat(net.getLinks().isEmpty(), is(true));
    }

    @Test
    public void
    remove_LinkFromNode0ToNode1FromNetworkWith2LinksIncludingLinkFromNode0ToNode1_NetworkHoldsOtherLink() throws Exception {
        net = network(
                link(from(0), to(1), new DummyLabel()),
                link(from(0), to(2), new DummyLabel())
        );

        net.remove(dummyLink(0, 1));

        assertThat(net.getLinks(), containsInAnyOrder(dummyLink(0, 2)));
    }

    @Test
    public void
    remove_LinkFromNode0ToNode1FromNetworkWith2LinksIncludingLinkFromNode0ToNode1_NetworkContainsNode1() throws Exception {
        net = network(
                link(from(0), to(1), new DummyLabel()),
                link(from(0), to(2), new DummyLabel())
        );

        net.remove(dummyLink(0, 1));

        assertThat(net.getNodes(), containsInAnyOrder(new Node(0), new Node(1), new Node(2)));
    }

}