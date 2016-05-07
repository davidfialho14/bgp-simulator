package network;

import network.exceptions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import stubs.StubLabel;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static wrappers.StubWrapper.stubLink;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network net;

    @Before
    public void setUp() throws Exception {
        net = new Network(null);
    }

    @Test
    public void addNode_NodeWithId0_ContainsNodeWithId0() throws Exception {
        net.addNode(0);

        assertThat(net.getIds(), containsInAnyOrder(0));
    }

    @Test
    public void addNode_NodesWithIds0And1_ContainsNodesWithIds0And1() throws Exception {
        net.addNode(0); net.addNode(1);

        assertThat(net.getIds(), containsInAnyOrder(0, 1));
    }

    @Test
    public void addNode_NodeWithId0Twice_SecondAddNodeReturnsFalse() throws Exception {
        net.addNode(0);

        assertThat(net.addNode(0), is(false));
    }

    @Test
    public void addLink_Node0ToNode1BothAlreadyAddedToTheNetwork_ContainsLinkBetweenNode0AndNode1() throws Exception {
        net.addNode(0); net.addNode(1);

        net.addLink(0, 1, new StubLabel());

        assertThat(net.getLinks(), containsInAnyOrder(stubLink(0, 1)));
    }

    @Test
    public void addLink_Node0ToNode1ButNode0WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        net.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        net.addLink(0, 1, new StubLabel());
    }

    @Test
    public void addLink_Node0ToNode1ButNode1WasNotAddedToTheNetwork_ThrowsNodeNotFoundException() throws Exception {
        net.addNode(0);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '1' does not exist");
        net.addLink(0, 1, new StubLabel());
    }

    @Test
    public void addLink_Node0ToNode1Twice_ContainsOnlyOneLink() throws Exception {
        net.addNode(0); net.addNode(1);

        net.addLink(0, 1, new StubLabel());
        net.addLink(0, 1, new StubLabel());

        assertThat(net.getLinks().size(), is(1));
    }

    @Test
    public void addLink_Node0ToNode0_ContainsLinkFromNode0ToNode0() throws Exception {
        net.addNode(0);

        net.addLink(0, 0, new StubLabel());

        assertThat(net.getLinks(), containsInAnyOrder(stubLink(0, 0)));
    }

    @Test
    public void removeLink_FromNode0ToNode0_DoesNotContainLinkFromNode0ToNode0() throws Exception {
        net.addNode(0);
        net.addLink(stubLink(0, 0));

        net.removeLink(stubLink(0, 0));

        assertThat(net.getLinks(), not(containsInAnyOrder(stubLink(0, 0))));
    }

}