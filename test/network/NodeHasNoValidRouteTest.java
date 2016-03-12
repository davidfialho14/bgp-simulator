package network;

import org.junit.Before;
import org.junit.Test;
import policies.DummyAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class NodeHasNoValidRouteTest extends NodeTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        node.selectedAttributes.put(destination, attributeFactory.createInvalid());
        node.selectedPaths.put(destination, PathAttribute.createInvalid());
    }

    @Test
    public void
    learn_ValidRouteWhenNodeHasNoValidRouteForDestination_ExportsExtendedValidRoute()
            throws Exception {
        Route extendedRoute = new Route(destination, new DummyAttribute(), new PathAttribute(exportingNode));
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(stubProtocol.extend(outLink, learnedRoute.getAttribute())).thenReturn(extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_InvalidRouteWhenNodeHasNoValidRouteForDestination_ExportsIsNotCalled() throws Exception {
        Route learnedRoute = Route.createInvalid(destination, attributeFactory);
        when(stubProtocol.extend(outLink, attributeFactory.createInvalid())).thenReturn(learnedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

}