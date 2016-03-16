package network;

import org.junit.Before;
import org.junit.Test;
import policies.DummyAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for the Node class in the situation where the node has no valid route for the destination.
 */
public class NodeHasNoValidRouteTest extends NodeTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        node.selectedAttributes.put(destination, attributeFactory.createInvalid());
        node.selectedPaths.put(destination, PathAttribute.createInvalidPath());
    }

    @Test
    public void
    learn_ValidRouteFromAnyNeighbour_ExportsExtendedValidRoute()
            throws Exception {
        Route extendedRoute = new Route(destination, new DummyAttribute(), new PathAttribute(exportingNode));
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(stubProtocol.extend(any(), any())).thenReturn(extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_InvalidRouteFromAnyNeighbour_ExportsIsNotCalled() throws Exception {
        Route learnedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubProtocol.extend(any(), any())).thenReturn(attributeFactory.createInvalid());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

}