package core;

import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import protocols.Protocol;

import static core.Route.invalidRoute;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static policies.InvalidAttribute.invalid;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.StubWrapper.*;
import static wrappers.network.NetworkWrapper.anyNode;

@RunWith(MockitoJUnitRunner.class)
public class EngineLearnTest {

    @Mock
    private NodeState stubNodeState;

    @Mock
    private Protocol stubProtocol;

    private Engine engine;
    private Node destination = anyNode();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(null);
        when(stubNodeState.getProtocol()).thenReturn(stubProtocol);
    }

    @Test
    public void
    learn_InvalidRoute_InvalidRoute() throws Exception {
        when(stubProtocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(stubNodeState, anyStubLink(), invalidRoute(destination)),
                is(invalidRoute(destination)));
    }

    @Test
    public void
    learn_RouteWithAttributeThatExtendsToInvalid_InvalidRoute() throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path());
        when(stubProtocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(stubNodeState, anyStubLink(), exportedRoute), is(invalidRoute(destination)));
    }

    @Test
    public void
    learn_FromNode0RouteWithEmptyPathAndAttribute0WhichExtendsToAttribute1_Route1AndPathWithNode0() throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path());
        when(stubProtocol.extend(eq(destination), any(), any())).thenReturn(stubAttr(1));

        assertThat(engine.learn(stubNodeState, stubLink(1, 0), exportedRoute),
                is(route(destination, stubAttr(1), path(0))));
    }

    @Test
    public void
    learn_FromNode0RouteWithPathWithNode1AndAttribute0WhichExtendsToAttribute1_Route1AndPathWithNodes0And1()
            throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path(1));
        when(stubProtocol.extend(eq(destination), any(), any())).thenReturn(stubAttr(1));

        assertThat(engine.learn(stubNodeState, stubLink(1, 0), exportedRoute),
                is(route(destination, stubAttr(1), path(0, 1))));
    }
}