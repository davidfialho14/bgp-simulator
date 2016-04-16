package simulation;

import network.Link;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.Attribute;
import policies.DummyAttribute;
import policies.DummyLabel;
import protocols.Protocol;

import static network.Factory.createRandomNode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static policies.InvalidAttribute.invalid;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineSelectTest {

    SimulateEngine engine;  // class under test

    Node destination = createRandomNode();
    Node learningNode = createRandomNode();
    Node exportingNode = createRandomNode();
    Link link = new Link(learningNode, exportingNode, new DummyLabel());

    @Mock
    NodeStateInfo stubNodeStateInfo;

    @Mock
    Protocol stubProtocol;

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(stubProtocol, null, null, null);
        when(stubProtocol.isOscillation(any(), any(), any(), any(), any())).thenReturn(false);
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndExclRouteInvalid_InvalidRoute() throws Exception {
        Route invalidRoute = Route.createInvalid(destination);
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(invalidRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, invalidRoute), is(invalidRoute));
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = Route.createInvalid(destination);
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclRouteInvalid_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = Route.createInvalid(destination);
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclRouteWithAttr1AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr1AndEmptyPathAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithDestinationAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(destination));
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndExclRouteWithAttr0AndPathWithDestination_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(destination));
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndExclRouteInvalid_InvalidRoute()
            throws Exception {
        Route invalidRoute = Route.createInvalid(destination);
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(learningNode));
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(invalidRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(invalidRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(learningNode));
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndExclRouteWithAttr0AndPathWith2_RouteWithAttr0AndPathWith2()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(learningNode));
        Node[] nodes = {destination, createRandomNode()};
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(nodes));
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr1AndPathWithLearningNodeAndExclRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(learningNode));
        Route exlcRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndExclRouteWithAttr1AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(learningNode));
        Route exlcRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);

        assertThat(engine.select(stubNodeStateInfo, link, null, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndExclRouteIsValid_TableUpdatedWithInvalidRoute()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute(learningNode));
        Route exlcRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);
        PathAttribute invalidPath = PathAttribute.invalidPath();

        engine.select(stubNodeStateInfo, link, null, learnedRoute);

        verify(stubNodeStateInfo, times(1)).updateRoute(destination, exportingNode, invalid(), invalidPath);
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndExclRouteWithAttr1BothWithEmptyPaths_TableUpdatedWithRouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = new Route(destination, new DummyAttribute(0), new PathAttribute());
        Route exlcRoute = new Route(destination, new DummyAttribute(1), new PathAttribute());
        when(stubNodeStateInfo.getSelectedRoute(any(), any())).thenReturn(exlcRoute);
        Attribute expectedAttribute = new DummyAttribute(0);
        PathAttribute expectedPath = new PathAttribute();

        engine.select(stubNodeStateInfo, link, null, learnedRoute);

        verify(stubNodeStateInfo, times(1)).updateRoute(destination, exportingNode, expectedAttribute, expectedPath);
    }
}