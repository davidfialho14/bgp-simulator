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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NodeTest {

    @Mock
    private Network mockNetwork;
    @Mock
    private DummyProtocol stubProtocol;
    @Mock
    private RouteTable stubRouteTable;

    private DummyAttributeFactory attributeFactory = new DummyAttributeFactory();
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

    private void setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable() {
        node = new Node(mockNetwork, 0, this.stubProtocol);
        node.startTable();
        node.routeTable = stubRouteTable;
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
        node.selectedAttributes.put(destination, attributeFactory.createInvalid());
        node.selectedPaths.put(destination, PathAttribute.createInvalid());
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(new DummyAttribute());
        Route selectedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubRouteTable.getSelectedRoute(destination, exportingNode)).thenReturn(selectedRoute);

        Route extendedRoute = new Route(destination, new DummyAttribute(), new PathAttribute(exportingNode));
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_InvalidRouteWhenNodeHasNoValidRouteForDestination_ExportsIsNotCalled() throws Exception {
        setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable();
        node.selectedAttributes.put(destination, attributeFactory.createInvalid());
        node.selectedPaths.put(destination, PathAttribute.createInvalid());
        Route learnedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(attributeFactory.createInvalid());
        Route selectedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    // TODO learns route not preferred -> does not export

}