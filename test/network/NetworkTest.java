package network;

import implementations.policies.ShortestPathLabel;
import implementations.protocols.BGPNodeFactory;
import network.exceptions.NodeExistsException;
import network.exceptions.NodeNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class NetworkTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Network network;

    /**
     * Factory class to create instances necessary for abstract and interface classes necessary to build a network.
     * The created elements are guaranteed to be able to work together.
     */
    private static class Factory {
        static NodeFactory createNodeFactory() {
            return new BGPNodeFactory();
        }

        /**
         * Creates any label. To be used when the specific value of the label does not matter.
         * @return a label instance.
         */
        static Label createLabel() {
            return new ShortestPathLabel(0);
        }

        /**
         * Creates a link between two nodes of a network with any label. To be used when a link is needed but its
         * label can be anything.
         * @param network network where node are to be linked.
         * @param srcId id of the source node.
         * @param destId id of the destination node.
         * @return link instance.
         */
        static Link createLink(Network network, int srcId, int destId) {
            NodeFactory factory = createNodeFactory();

            // do not care about the link length
            return new Link(factory.createNode(network, srcId), factory.createNode(network, destId), createLabel());
        }
    }

    @Before
    public void setUp() throws Exception {
        network = new Network(Factory.createNodeFactory());
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
        network.link(0, 1, Factory.createLabel());

        assertThat(network.getLinks(), containsInAnyOrder(expectedLinks));
    }

    @Test
    public void linkExistingSourceToNonExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, Factory.createLabel());
    }

    @Test
    public void linkNonExistingSourceToExistingDestination_ThrowsNodeNotFoundException() throws Exception {
        network.addNode(1);

        thrown.expect(NodeNotFoundException.class);
        thrown.expectMessage("node with id '0' does not exist");
        network.link(0, 1, Factory.createLabel());
    }

}