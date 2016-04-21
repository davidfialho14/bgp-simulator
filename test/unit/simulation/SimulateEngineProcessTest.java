package simulation;

import network.Link;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.Attribute;
import simulation.implementations.handlers.NullEventHandler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static policies.InvalidAttribute.invalid;
import static simulation.PathAttribute.invalidPath;
import static wrappers.DummyWrapper.dummyAttr;
import static wrappers.DummyWrapper.dummyLink;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.anyRoute;
import static wrappers.RouteWrapper.route;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineProcessTest {

    private SimulateEngine engine;
    private EventHandler eventHandler = new NullEventHandler();

    @Mock
    private NodeStateInfo nodeStateInfo;

    private final Node destination = new Node(0);
    private final Link link = dummyLink(1, 2);
    private final Route invalidRoute = Route.invalidRoute(destination);

    @Before
    public void setUp() throws Exception {
        engine = spy(new SimulateEngine(null, null, null, eventHandler));
        // stub out the learn method
        doReturn(anyRoute(destination)).when(engine).learn(any(), any());
    }

    /**
     * Sets the previously selected attribute and path for the node state info.
     *  @param attribute attribute previously selected.
     * @param path path previously selected.
     */
    private void setPreviouslySelected(Attribute attribute, PathAttribute path) {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(attribute);
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(path);
    }
    
    @Test
    public void
    process_PrevSelectedInvalidRouteAndSelectedInvalidRoute_DoesNotExport() throws Exception {
        setPreviouslySelected(invalid(), invalidPath());
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, never()).exportToInNeighbours(any(), any(), any(), any());
    }

    @Test
    public void
    process_PrevSelectedInvalidRouteAndSelectedValidRoute_ExportsSelectedRoute() throws Exception {
        setPreviouslySelected(invalid(), invalidPath());
        Route selectedRoute = route(destination, dummyAttr(0), path());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedValidRouteAndSelectedInvalidRoute_ExportsInvalidRoute() throws Exception {
        setPreviouslySelected(dummyAttr(0), path());
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(invalidRoute), any(), any());
    }

    @Test
    public void
    process_DestinationIsNotKnownAndSelectedInvalidRoute_ExportsInvalidRoute() throws Exception {
        setPreviouslySelected(null, null);
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(invalidRoute), any(), any());
    }

    @Test
    public void
    process_DestinationIsNotKnownAndSelectedValidRoute_ExportsSelectedRoute() throws Exception {
        setPreviouslySelected(null, null);
        Route selectedRoute = route(destination, dummyAttr(0), path());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedRouteWithAttr0AndEmptyPathAndSelectedRouteWithAttr1AndEmptyPath_ExportsSelectedRoute()
            throws Exception {
        setPreviouslySelected(dummyAttr(0), path());
        Route selectedRoute = route(destination, dummyAttr(1), path());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedRouteWithAttr0AndEmptyPathAndSelectedRouteWithAttr0AndPathWithOneNode_ExportsSelectedRoute()
            throws Exception {
        setPreviouslySelected(dummyAttr(0), path());
        Route selectedRoute = route(destination, dummyAttr(0), path(0));
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_RouteBetterThanCurrentBestRouteAndWithDestinationEqualToLinkSourceNode_ExportsLearnedRoute()
            throws Exception {
        Route learnedRoute = route(0, dummyAttr(-1), path());
        doReturn(learnedRoute).when(engine).learn(any(), any());
        setPreviouslySelected(dummyAttr(0), path());
        doReturn(learnedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(anyRoute(0), dummyLink(0, 2), 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(learnedRoute), any(), any());
    }
}