package core;

import core.topology.Link;
import core.topology.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static core.InvalidAttribute.invalidAttr;
import static core.InvalidPath.invalidPath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.StubWrapper.stubAttr;
import static wrappers.StubWrapper.stubLink;

@RunWith(MockitoJUnitRunner.class)
public class EngineSelectTest {

    private Engine engine;  // class under test
    
    private Link importLink = stubLink(1, 2);

    @Mock Protocol stubProtocol;
    @Spy RouteTable stubRouteTable = new RouteTable(Node.newNode(0));
    @InjectMocks private NodeState nodeState = new NodeState();

    private void setAlternativeRoute(Route alternativeRoute) {
        // link must be different from the import link
        stubRouteTable.setRoute(stubLink(1, 0), alternativeRoute);
    }
    
    @Before
    public void setUp() throws Exception {
        engine = new Engine(null);
        when(stubProtocol.isPolicyDispute(any(), any(), any(), any())).thenReturn(false);
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndAltRouteInvalid_InvalidRoute() throws Exception {
        Route invalidRoute = Route.invalidRoute(Node.newNode(0));
        setAlternativeRoute(invalidRoute);

        assertThat(engine.select(nodeState, importLink, invalidRoute), is(invalidRoute));
    }

    @Test
    public void
    select_BetweenLearnedInvalidRouteAndAltRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = Route.invalidRoute(Node.newNode(0));
        Route exlcRoute = route(0, stubAttr(0), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndAltRouteInvalid_RouteWithAttr0AndEmptyPath() throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path());
        Route exlcRoute = Route.invalidRoute(Node.newNode(0));
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndAltRouteWithAttr1AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path());
        Route exlcRoute = route(0, stubAttr(1), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr1AndEmptyPathAndAltRouteWithAttr0AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(1), path());
        Route exlcRoute = route(0, stubAttr(0), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithDestinationAndAltRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path(0));
        Route exlcRoute = route(0, stubAttr(0), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndEmptyPathAndAltRouteWithAttr0AndPathWithDestination_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path());
        Route exlcRoute = route(0, stubAttr(0), path(0));
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(learnedRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndAltRouteInvalid_InvalidRoute()
            throws Exception {
        Route invalidRoute = Route.invalidRoute(Node.newNode(0));
        Route learnedRoute = route(0, stubAttr(0), path(1));
        setAlternativeRoute(invalidRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(invalidRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndAltRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path(1));
        Route exlcRoute = route(0, stubAttr(0), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndAltRouteWithAttr0AndPathWith2_RouteWithAttr0AndPathWith2()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path(1));
        Route exlcRoute = route(0, stubAttr(0), path(0, 3));
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr1AndPathWithLearningNodeAndAltRouteWithAttr0AndEmptyPath_RouteWithAttr0AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(1), path(1));
        Route exlcRoute = route(0, stubAttr(0), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndAltRouteWithAttr1AndEmptyPath_RouteWithAttr1AndEmptyPath()
            throws Exception {
        Route learnedRoute = route(0, stubAttr(0), path(1));
        Route exlcRoute = route(0, stubAttr(1), path());
        setAlternativeRoute(exlcRoute);

        assertThat(engine.select(nodeState, importLink, learnedRoute), is(exlcRoute));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndPathWithLearningNodeAndAltRouteIsValid_TableUpdatedWithInvalidRoute()
            throws Exception {
        setAlternativeRoute(route(0, stubAttr(1), path()));

        engine.select(nodeState, importLink, route(0, stubAttr(0), path(1)));

        verify(stubRouteTable, times(1)).setRoute(importLink, route(0, invalidAttr(), invalidPath()));
    }

    @Test
    public void
    select_BetweenLearnedWithAttr0AndAltRouteWithAttr1BothWithEmptyPaths_TableUpdatedWithRouteWithAttr0AndEmptyPath()
            throws Exception {
        setAlternativeRoute(route(0, stubAttr(1), path()));

        engine.select(nodeState, importLink, route(0, stubAttr(0), path()));

        verify(stubRouteTable, times(1)).setRoute(importLink, route(0, stubAttr(0), path()));
    }
}