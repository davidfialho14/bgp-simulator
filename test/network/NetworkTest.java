package network;

import implementations.protocols.BGPNodeFactory;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

public class NetworkTest {

    @Test
    public void addAnyNodeToTheNetwork_NetworkContainsAddedNode() {
        Network network = new Network(new BGPNodeFactory());

        Integer[] expectedIds = {0};
        network.addNode(0);

        assertThat(network.getIds(), containsInAnyOrder(expectedIds));
    }

}