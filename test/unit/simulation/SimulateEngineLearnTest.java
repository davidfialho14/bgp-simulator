package simulation;

import network.Factory;
import network.Link;
import network.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import policies.DummyAttribute;
import protocols.Protocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static policies.InvalidAttribute.invalid;

@RunWith(MockitoJUnitRunner.class)
public class SimulateEngineLearnTest {

    @Mock
    Protocol protocol;

    @InjectMocks
    SimulateEngine engine;

    Node destination = Factory.createRandomNode();

    @Test
    public void
    learn_FromAnyNodeInvalidRoute_InvalidRoute() throws Exception {
        Link link = Factory.createRandomLink();
        Route invalidRoute = Route.createInvalid(destination);
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());

        assertThat(engine.learn(link, invalidRoute), is(invalidRoute));
    }

    @Test
    public void
    learn_FromAnyNodeRouteWithAttrThatExtendsToInvalid_InvalidRoute() throws Exception {
        Link link = Factory.createRandomLink();
        Route exportedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(invalid());


        Route invalidRoute = Route.createInvalid(destination);
        assertThat(engine.learn(link, exportedRoute), is(invalidRoute));
    }

    @Test
    public void
    learn_FromNode0RouteWithEmptyPathAndValidAttrWhichExtendsToAttr1_RouteWithPathWithNode0AndAttr1() throws Exception {
        Link link = Factory.createLink(1, 0);
        Route exportedRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        when(protocol.extend(eq(destination), any(), any())).thenReturn(new DummyAttribute(1));

        Route expectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(new Node(0)));
        assertThat(engine.learn(link, exportedRoute), is(expectedRoute));
    }

    @Test
    public void
    learn_FromNode0RouteWithPathWithNode1AndValidAttrWhichExtendsToAttr1_RouteWithAttr1AndPathWithNode0AndNode1()
            throws Exception {
        Link link = Factory.createLink(1, 0);
        Node node1 = new Node(1);
        Route exportedRoute = new Route(destination, new DummyAttribute(), new PathAttribute(node1));
        when(protocol.extend(eq(destination), any(), any())).thenReturn(new DummyAttribute(1));
        Node[] pathNodes = {node1, new Node(0)};

        Route expectedRoute = new Route(destination, new DummyAttribute(1), new PathAttribute(pathNodes));
        assertThat(engine.learn(link, exportedRoute), is(expectedRoute));
    }
}