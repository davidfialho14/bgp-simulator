package simulation;

import network.Factory;
import network.Link;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;
import policies.DummyLabel;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineSelectTest {

    SimulateEngine engine;  // class under test

    Node destination = Factory.createNode(0);
    Node learningNode = Factory.createNode(1);
    Node exportingNode = Factory.createNode(2);
    Link link = new Link(learningNode, exportingNode, new DummyLabel());

    @Mock
    RouteTable stubRouteTable;
    @InjectMocks
    NodeStateInfo nodeStateInfo = new NodeStateInfo(learningNode, new DummyAttributeFactory());

    @Mock
    Protocol stubProtocol;

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(stubProtocol, new DummyAttributeFactory(), null, null);
        when(stubProtocol.isOscillation(any(), any(), any(), any(), any())).thenReturn(false);
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndExclInvalidRoute_InvalidRoute() throws Exception {
        Route invalidRoute = Route.createInvalid(destination, new DummyAttributeFactory());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(invalidRoute);

        assertThat(engine.select(nodeStateInfo, link, null, invalidRoute), is(invalidRoute));
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = Route.createInvalid(destination, new DummyAttributeFactory());
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclInvalidRoute_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = Route.createInvalid(destination, new DummyAttributeFactory());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclRouteWithAttr1AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr1AndEmptyPathAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithDestinationAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(destination));
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclRouteWithAttr0AndPathWithDestination_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(destination));
        when(stubRouteTable.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(nodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }
}