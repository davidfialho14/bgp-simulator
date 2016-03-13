package network;

import org.junit.Test;
import policies.DummyAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
    learn_FromNotSelectedNeighbourRouteWhichExtendsToRoute1PreferredToSelected_ExportsRoute1() throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_FromNotSelectedNeighbourRouteWhichExtendsToRoute1NotPreferredToSelected_DoesNotExport() throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToRoute1PreferredToSelected_ExportsRoute1() throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(2), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                new Route(destination, new DummyAttribute(0), new PathAttribute())
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToRoute1NotPreferredToSelected_ExportsRoute1() throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(2), new PathAttribute());
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                new Route(destination, new DummyAttribute(0), new PathAttribute())
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToRoute1EqualToSelected_DoesNotExport() throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
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
    learn_FromSelectedNeighbourRouteWhichExtendsToInvalidRouteAndNoOtherNeighbourHasValidRoute_ExportsInvalid()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(
                Route.createInvalid(destination, attributeFactory)
        );

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToInvalidRouteAndThereIsNoOtherNeighbour_ExportsInvalid()
            throws Exception {
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(selectedRoute, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(null);   // no other neighbour

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(extendedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToInvalidButOtherNeighbourHasValidRoute1EqualToPrevious_DoesNotExport()
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
    learn_FromSelectedNeighbourRouteWhichExtendsToInvalidRouteButSelectedRouteWasAlreadyInvalid_DoesNotExport()
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

    @Test
    public void
    learn_FromSelectedNeighbourRouteWhichExtendsToInvalidAndOtherNeighbourHasValidRoute1DiffFromPrevious_ExportsRoute1()
            throws Exception {
        Route previouslySelected = new Route(destination, new DummyAttribute(2), new PathAttribute());
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route extendedRoute = Route.createInvalid(destination, attributeFactory);
        setup(previouslySelected, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(selectedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWithCycleAndOtherNeighbourHasValidRoute1_ExportsRoute1() throws Exception {
        Route previouslySelected = new Route(destination, new DummyAttribute(1), new PathAttribute(destination));
        Route exclSelectedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(destination));
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        learnedRoute.getPath().add(node);   // put cycle in the learned route
        setup(previouslySelected, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exclSelectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(exclSelectedRoute));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWithCycleAndOtherNeighbourHasInvalidRoute_ExportsInvalidRoute() throws Exception {
        Route previouslySelected = new Route(destination, new DummyAttribute(1), new PathAttribute(destination));
        Route exclSelectedRoute = Route.createInvalid(destination, attributeFactory);
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        learnedRoute.getPath().add(node);   // put cycle in the learned route
        setup(previouslySelected, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exclSelectedRoute);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(Route.createInvalid(destination, attributeFactory)));
    }

    @Test
    public void
    learn_FromSelectedNeighbourRouteWithCycleAndThereIsNoOtherNeighbour_ExportsInvalidRoute() throws Exception {
        Route previouslySelected = new Route(destination, new DummyAttribute(1), new PathAttribute(destination));
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        learnedRoute.getPath().add(node);   // put cycle in the learned route
        setup(previouslySelected, learnedRoute.getAttribute(), extendedRoute.getAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(null);

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(any(), eq(Route.createInvalid(destination, attributeFactory)));
    }

}
