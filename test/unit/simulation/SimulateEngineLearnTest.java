package simulation;

import network.Link;
import network.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import dummies.DummyAttribute;
import protocols.Protocol;

import static network.Factory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static policies.InvalidAttribute.invalid;
import static wrappers.PathWrapper.path;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineLearnTest {

    @Mock
    Protocol protocol;

    @InjectMocks
    SimulateEngine engine;

    Node destination = createRandomNode();

    @Test
    public void
    learn_FromAnyNodeInvalidRoute_InvalidRoute() throws Exception {
        Link link = createRandomLink();
        Route invalidRoute = Route.invalidRoute(destination);
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(link, invalidRoute), is(invalidRoute));
    }

    @Test
    public void
    learn_FromAnyNodeRouteWithAttrThatExtendsToInvalid_InvalidRoute() throws Exception {
        Link link = createRandomLink();
        Route exportedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());


        Route invalidRoute = Route.invalidRoute(destination);
        assertThat(engine.learn(link, exportedRoute), is(invalidRoute));
    }

    @Test
    public void
    learn_FromNode0RouteWithEmptyPathAndValidAttrWhichExtendsToAttr1_RouteWithPathWithNode0AndAttr1() throws Exception {
        Link link = createLink(1, 0);
        Route exportedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(new DummyAttribute(1));

        Route expectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(new Node(0)));
        assertThat(engine.learn(link, exportedRoute), is(expectedRoute));
    }

    @Test
    public void
    learn_FromNode0RouteWithPathWithNode1AndValidAttrWhichExtendsToAttr1_RouteWithAttr1AndPathWithNode0AndNode1()
            throws Exception {
        Link link = createLink(1, 0);
        Route exportedRoute = new Route(destination, new DummyAttribute(), path(1));
        when(protocol.extend(eq(destination), any(), any())).thenReturn(new DummyAttribute(1));

        Route expectedRoute = new Route(destination, new DummyAttribute(1), path(0, 1));
        assertThat(engine.learn(link, exportedRoute), is(expectedRoute));
    }
}