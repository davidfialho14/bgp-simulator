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
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

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

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRoutePreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsExtendedRoute1()
            throws Exception {
        setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable();
        Route selectedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        node.selectedAttributes.put(destination, selectedRoute.getAttribute());
        node.selectedPaths.put(destination, selectedRoute.getPath());
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        Attribute extendedAttribute = new DummyAttribute(1);
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(extendedAttribute);
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        Route extendedRoute = new Route(destination, extendedAttribute, new PathAttribute(exportingNode));
        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRouteNotPreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsNotCalled()
            throws Exception {
        setNodeLinkedBothWaysWithANeighbourAndWithEmptyTable();
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        node.selectedAttributes.put(destination, selectedRoute.getAttribute());
        node.selectedPaths.put(destination, selectedRoute.getPath());
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        Attribute extendedAttribute = new DummyAttribute(0);
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(extendedAttribute);
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    // TODO last two test but for same neighbour
    // TODO learns route that extends to invalid route -> not exported

}