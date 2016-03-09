package network;

import implementations.protocols.BGPNodeFactory;
import network.exceptions.NodeExistsException;
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

    @Before
    public void setUp() throws Exception {
        network = new Network(new BGPNodeFactory());
    }

    @Test
    public void addAnyNode_NetworkContainsAddedNode() throws NodeExistsException {
        Integer[] expectedIds = {0};
        network.addNode(0);

        assertThat(network.getIds(), containsInAnyOrder(expectedIds));
    }

    @Test
    public void addAny2Nodes_NetworkContainsThe2Nodes() throws NodeExistsException {
        Integer[] expectedIds = {0, 1};
        network.addNode(0); network.addNode(1);

        assertThat(network.getIds(), containsInAnyOrder(expectedIds));
    }

    @Test
    public void addTheSameNodeTwice_NetworkContainsAddedNode() throws NodeExistsException {
        network.addNode(0);

        thrown.expect(NodeExistsException.class);
        thrown.expectMessage("node with id '0' already exists");
        network.addNode(0);
    }

}