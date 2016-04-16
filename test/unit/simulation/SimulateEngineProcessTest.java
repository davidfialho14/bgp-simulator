package simulation;

import network.Factory;
import network.Link;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.DummyAttribute;
import policies.DummyLabel;
import simulation.implementations.handlers.DebugEventHandler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static policies.InvalidAttribute.invalid;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineProcessTest {

    SimulateEngine engine;

    @Mock
    NodeStateInfo nodeStateInfo;

    final Node destination = Factory.createRandomNode();
    final Node learningNode = Factory.createRandomNode();
    final Node exportingNode = Factory.createRandomNode();
    final Link link = new Link(learningNode, exportingNode, new DummyLabel());
    final Route invalidRoute = Route.createInvalid(destination);

    @Before
    public void setUp() throws Exception {
        engine = spy(new SimulateEngine(null, null, null, new DebugEventHandler()));
        // stub out the learn method
        doReturn(Factory.createRandomRoute()).when(engine).learn(any(), any());
    }

    @Test
    public void
    process_PrevSelectedInvalidRouteAndSelectedInvalidRoute_DoesNotExportToInNeighbours() throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(invalid());
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(PathAttribute.createInvalidPath());
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, never()).exportToInNeighbours(any(), any(), any(), any());
    }

    @Test
    public void
    process_PrevSelectedValidRouteAndSelectedInvalidRoute_ExportsToInNeighboursInvalidRoute() throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(new DummyAttribute());
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(new PathAttribute());
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(invalidRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedInvalidRouteAndSelectedValidRoute_ExportsToInNeighboursValidRoute() throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(invalid());
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(PathAttribute.createInvalidPath());
        Route selectedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_DestinationIsNotKnownAndSelectedInvalidRoute_ExportsToInNeighboursInvalidRoute() throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(null);
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(null);
        doReturn(invalidRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(invalidRoute), any(), any());
    }

    @Test
    public void
    process_DestinationIsNotKnownAndSelectedValidRoute_ExportsToInNeighboursValidRoute() throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(null);
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(null);
        Route selectedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedRouteWithAttr0AndEmptyPathAndSelectedRouteWithAttr1AndEmptyPath_ExportsSelectedRoute()
            throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(new DummyAttribute(0));
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(new PathAttribute());
        Route selectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }

    @Test
    public void
    process_PrevSelectedRouteWithAttr0AndEmptyPathAndSelectedRouteWithAttr0AndPathWithOneNode_ExportsSelectedRoute()
            throws Exception {
        when(nodeStateInfo.getSelectedAttribute(any())).thenReturn(new DummyAttribute(0));
        when(nodeStateInfo.getSelectedPath(any())).thenReturn(new PathAttribute());
        Route selectedRoute = new Route(destination,
                new DummyAttribute(0), new PathAttribute(Factory.createRandomNode()));
        doReturn(selectedRoute).when(engine).select(any(), any(), any(), any());

        engine.process(nodeStateInfo, new ScheduledRoute(invalidRoute, link, 0));

        verify(engine, times(1)).exportToInNeighbours(any(), eq(selectedRoute), any(), any());
    }
}