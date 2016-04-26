package simulation;

import network.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import protocols.Protocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static policies.InvalidAttribute.invalid;
import static simulation.Route.invalidRoute;
import static wrappers.StubWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.network.NetworkWrapper.anyNode;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineLearnTest {

    @Mock
    Protocol protocol;

    @InjectMocks
    SimulateEngine engine;

    Node destination = anyNode();

    @Test
    public void
    learn_InvalidRoute_InvalidRoute() throws Exception {
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(anyStubLink(), invalidRoute(destination)), is(invalidRoute(destination)));
    }

    @Test
    public void
    learn_RouteWithAttributeThatExtendsToInvalid_InvalidRoute() throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(anyStubLink(), exportedRoute), is(invalidRoute(destination)));
    }

    @Test
    public void
    learn_FromNode0RouteWithEmptyPathAndAttribute0WhichExtendsToAttribute1_Route1AndPathWithNode0() throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(stubAttr(1));

        assertThat(engine.learn(stubLink(1, 0), exportedRoute), is(route(destination, stubAttr(1), path(0))));
    }

    @Test
    public void
    learn_FromNode0RouteWithPathWithNode1AndAttribute0WhichExtendsToAttribute1_Route1AndPathWithNodes0And1()
            throws Exception {
        Route exportedRoute = route(destination, stubAttr(0), path(1));
        when(protocol.extend(eq(destination), any(), any())).thenReturn(stubAttr(1));

        assertThat(engine.learn(stubLink(1, 0), exportedRoute), is(route(destination, stubAttr(1), path(0, 1))));
    }
}