package network;

import org.junit.Test;
import policies.DummyAttribute;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NodeHasValidRouteTest extends NodeTest {

    private void setup(Attribute selectedAttribute, Attribute learnedAttribute, Attribute extendedAttribute) {
        Route selectedRoute = new Route(destination, selectedAttribute, new PathAttribute());
        node.selectedAttributes.put(destination, selectedRoute.getAttribute());
        node.selectedPaths.put(destination, selectedRoute.getPath());
        when(stubProtocol.extend(outLink, learnedAttribute)).thenReturn(extendedAttribute);
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(selectedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRoutePreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsExtendedRoute1()
            throws Exception {
        Route extendedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(exportingNode));
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        setup(new DummyAttribute(0), learnedRoute.getAttribute(), extendedRoute.getAttribute());

        node.learn(outLink, learnedRoute);

        verify(mockNetwork).export(inLink, extendedRoute);
    }

    @Test
    public void
    learn_Route1FromNeighbour1ExtendsToRouteNotPreferredToCurrentSelectedRouteFromOtherNeighbour_ExportsNotCalled()
            throws Exception {
        Route extendedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(exportingNode));
        Route learnedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        setup(new DummyAttribute(1), learnedRoute.getAttribute(), extendedRoute.getAttribute());

        node.learn(outLink, learnedRoute);

        verify(mockNetwork, never()).export(any(), any());
    }

}
