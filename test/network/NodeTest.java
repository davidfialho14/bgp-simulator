package network;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;
import policies.DummyLabel;
import protocols.DummyProtocol;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NodeTest {

    @Mock
    Network mockNetwork;
    @Mock
    DummyProtocol stubProtocol;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mockNetwork.getAttrFactory()).thenReturn(new DummyAttributeFactory());
    }

    private Node createNodeWithEmptyRouteTable(Network network) {
        Node node = new Node(network, 0, this.stubProtocol);
        node.routeTable = new RouteTable(node.getOutNeighbours(), network.getAttrFactory());
        return node;
    }

    @Test
    public void
    learn_NodeLinkedBothWaysWithANeighbourAndWithEmptyTableLearnsValidRoute_ExportsExtendedValidRoute()
            throws Exception {
        Node node = createNodeWithEmptyRouteTable(mockNetwork);
        Node exportingNode = new Node(mockNetwork, 1, stubProtocol);
        Link outLink = new Link(node, exportingNode, new DummyLabel());
        node.addOutLink(outLink);
        Link inLink = new Link(exportingNode, node, new DummyLabel());
        node.addInLink(inLink);
        Route learnedRoute = new Route(exportingNode, new DummyAttribute(), new PathAttribute());
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(new DummyAttribute());

        Route expectedRoute = new Route(exportingNode, new DummyAttribute(), new PathAttribute(exportingNode));
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, expectedRoute);
    }

    @Test
    public void
    learn_NodeLinkedBothWaysWithANeighbourAndWithEmptyTableLearnsInValidRoute_ExportsInvalidRoute() throws Exception {
        Node node = createNodeWithEmptyRouteTable(mockNetwork);
        Node exportingNode = new Node(mockNetwork, 1, stubProtocol);
        Link outLink = new Link(node, exportingNode, new DummyLabel());
        node.addOutLink(outLink);
        Link inLink = new Link(exportingNode, node, new DummyLabel());
        node.addInLink(inLink);
        DummyAttributeFactory attributeFactory = new DummyAttributeFactory();
        Route learnedRoute = Route.createInvalid(exportingNode, attributeFactory);
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(attributeFactory.createInvalid());

        Route expectedRoute = Route.createInvalid(exportingNode, attributeFactory);
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, expectedRoute);
    }

    // TODO

}