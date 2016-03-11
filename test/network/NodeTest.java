package network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;
import policies.DummyLabel;
import protocols.DummyProtocol;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NodeTest {

    @Mock
    private Network mockNetwork;
    @Mock
    private DummyProtocol stubProtocol;
    @Mock
    private RouteTable stubRouteTable;

    private Node node;
    private Node exportingNode;
    private Node destination;
    private Link outLink;
    private Link inLink;

    @Before
    public void setUp() throws Exception {
        when(mockNetwork.getAttrFactory()).thenReturn(new DummyAttributeFactory());
    }

    @After
    public void tearDown() throws Exception {
        reset(stubProtocol);
        reset(stubRouteTable);
    }

    private Node createNodeWithEmptyRouteTable(Network network) {
        Node node = new Node(network, 0, this.stubProtocol);
        node.routeTable = stubRouteTable;
        return node;
    }

    private void setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable() {
        node = createNodeWithEmptyRouteTable(mockNetwork);
        exportingNode = new Node(mockNetwork, 1, stubProtocol);
        destination = new Node(mockNetwork, 2, stubProtocol);
        outLink = new Link(node, exportingNode, new DummyLabel());
        node.addOutLink(outLink);
        inLink = new Link(exportingNode, node, new DummyLabel());
        node.addInLink(inLink);
    }

    @Test
    public void
    learn_ValidRouteWhenNodeHasNoValidRouteForDestination_ExportsExtendedValidRoute()
            throws Exception {
        setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable();
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(new DummyAttribute());
        Route selectedRoute = Route.createInvalid(destination, new DummyAttributeFactory());
        when(stubRouteTable.getSelectedRoute(destination, exportingNode)).thenReturn(selectedRoute);

        Route extendedRoute = new Route(destination, new DummyAttribute(), new PathAttribute(exportingNode));
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_InvalidRouteWhenNodeHasNoValidRouteForDestination_ExportsInvalidRoute() throws Exception {
        setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable();
        DummyAttributeFactory attributeFactory = new DummyAttributeFactory();
        Route learnedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(attributeFactory.createInvalid());
        Route selectedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubRouteTable.getSelectedRoute(destination, exportingNode)).thenReturn(selectedRoute);

        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    // TODO learns route not preferred -> does not export

}