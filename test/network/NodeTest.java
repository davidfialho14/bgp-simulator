package network;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;
import policies.DummyLabel;
import protocols.DummyProtocolFactory;

import static org.mockito.Mockito.*;

public class NodeTest {

    @Mock
    Network mockNetwork;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockNetwork.getAttrFactory()).thenReturn(new DummyAttributeFactory());
    }

    private Node createNodeWithEmptyRouteTable(Network network, ProtocolFactory protocolFactory) {
        Node node = new Node(network, 0, protocolFactory.createProtocol(0));
        node.routeTable = new RouteTable(node.getOutNeighbours(), network.getAttrFactory());
        return node;
    }

    @Test
    public void
    learn_NodeLinkedBothWaysWithANeighbourAndWithEmptyTableLearnsValidRoute_ExportsValidRoute() throws Exception {
        DummyProtocolFactory protocolFactory = new DummyProtocolFactory();
        Node node = createNodeWithEmptyRouteTable(mockNetwork, protocolFactory);
        Node exportingNode = new Node(mockNetwork, 1, protocolFactory.createProtocol(1));
        Link outLink = new Link(node, exportingNode, new DummyLabel());
        node.addOutLink(outLink);
        Link inLink = new Link(exportingNode, node, new DummyLabel());
        node.addInLink(inLink);
        Route learnedRoute = new Route(exportingNode, new DummyAttribute(), new PathAttribute());

        Route expectedRoute = new Route(exportingNode, new DummyAttribute(), new PathAttribute(exportingNode));
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, expectedRoute);
    }

}