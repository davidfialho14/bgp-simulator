package network;

import org.junit.Test;
import policies.DummyAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NodeHasValidRouteTest extends NodeTest {

    private void setup(Route selectedRoute, Attribute learnedAttribute, Attribute extendedAttribute) {
        node.selectedAttributes.put(destination, selectedRoute.getAttribute());
        node.selectedPaths.put(destination, selectedRoute.getPath());
        when(stubProtocol.extend(outLink, learnedAttribute)).thenReturn(extendedAttribute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRoutePreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsExtendedRoute1()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRouteNotPreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsNotCalled()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRoutePreferredToCurrentSelectedRouteFromNeighbour1_ExportsExtendedRoute1()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(2), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                new Route(destination, new DummyAttribute(0), new PathAttribute())
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRouteNotPreferredToCurrentSelectedRouteFromNeighbour1_ExportsExtendedRoute1()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(2), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                new Route(destination, new DummyAttribute(0), new PathAttribute())
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRouteEqualToCurrentSelectedRouteFromNeighbour1_ExportNotCalled()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                new Route(destination, new DummyAttribute(0), new PathAttribute())
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToInvalidRouteAndThereIsNoOtherValidRoute_ExportsInvalid()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToInvalidRouteAndNeighbourHadValidRouteAndThereIsNoOtherNeighbour_ExportsInvalid()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(null);   // no other neighbour

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToInvalidRouteAndSelectedRouteCorrespondsToOtherNeighbour1IsValid_ExportNotCalled()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToInvalidRouteAndSelectedRouteIsInvalid_ExportNotCalled()
            throws Exception {
        Route selectedRoute = Route.createInvalid(destination, attributeFactory);
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

}
