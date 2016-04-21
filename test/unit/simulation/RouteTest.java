package simulation;

import network.Node;
import org.junit.Test;
import dummies.DummyAttribute;

import static network.Factory.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RouteTest {
    
    private Node destination = createRandomNode();

    @Test
    public void
    compareTo_InvalidRouteWithInvalidRoute_Equal() throws Exception {
        Route invalidRoute1 = Route.invalidRoute(destination);
        Route invalidRoute2 = Route.invalidRoute(destination);

        assertThat(invalidRoute1.compareTo(invalidRoute2), equalTo(0));
    }

    @Test
    public void
    compareTo_InvalidRouteWithValidRoute_Greater() throws Exception {
        Route invalidRoute = Route.invalidRoute(destination);
        Route validRoute = new Route(destination, new DummyAttribute(), new PathAttribute());

        assertThat(invalidRoute.compareTo(validRoute), greaterThan(0));
    }

    @Test
    public void
    compareTo_ValidRouteWithInvalidRoute_Lesser() throws Exception {
        Route validRoute = new Route(destination, new DummyAttribute(), new PathAttribute());
        Route invalidRoute = Route.invalidRoute(destination);

        assertThat(validRoute.compareTo(invalidRoute), lessThan(0));
    }

    @Test
    public void
    compareTo_RoutesWithEqualAttributesAndPaths_Equal() throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(), new PathAttribute());
        Route route2 = new Route(destination, new DummyAttribute(), new PathAttribute());

        assertThat(route1.compareTo(route2), equalTo(0));
    }

    @Test
    public void
    compareTo_RouteDummyAttribute1WithRouteDummyAttribute2BothWithEmptyPaths_Greater() throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route route2 = new Route(destination, new DummyAttribute(2), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteDummyAttribute2WithRouteDummyAttribute1BothWithEmptyPaths_Lesser() throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(2), new PathAttribute());
        Route route2 = new Route(destination, new DummyAttribute(1), new PathAttribute());

        assertThat(route1.compareTo(route2), lessThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndPathWithOneNodeWithRouteWithAttr0AndEmptyPath_Greater()
            throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(), new PathAttribute(createRandomNode()));
        Route route2 = new Route(destination, new DummyAttribute(), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndEmptyPathWithRouteWithAttr0AndPathWithOneNode_Lesser()
            throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(), new PathAttribute());
        Route route2 = new Route(destination, new DummyAttribute(), new PathAttribute(createRandomNode()));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndPathWithOneNodeWithRouteWithAttr1AndEmptyPath_Greater()
            throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(), new PathAttribute(createRandomNode()));
        Route route2 = new Route(destination, new DummyAttribute(1), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr1AndEmptyPathWithRouteWithAttr0AndPathWithOneNode_Lesser()
            throws Exception {
        Route route1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        Route route2 = new Route(destination, new DummyAttribute(), new PathAttribute(createRandomNode()));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

}