package network;

import org.junit.Test;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RouteTest {

    @Test
    public void
    compareTo_InvalidRouteWithInvalidRoute_Equal() throws Exception {
        Route invalidRoute1 = Route.createInvalid(null, new DummyAttributeFactory());
        Route invalidRoute2 = Route.createInvalid(null, new DummyAttributeFactory());

        assertThat(invalidRoute1.compareTo(invalidRoute2), equalTo(0));
    }

    @Test
    public void
    compareTo_InvalidRouteWithValidRoute_Lesser() throws Exception {
        Route invalidRoute = Route.createInvalid(null, new DummyAttributeFactory());
        Route validRoute = new Route(null, new DummyAttribute(), new PathAttribute());

        assertThat(invalidRoute.compareTo(validRoute), lessThan(0));
    }

    @Test
    public void
    compareTo_ValidRouteWithInvalidRoute_Greater() throws Exception {
        Route validRoute = new Route(null, new DummyAttribute(), new PathAttribute());
        Route invalidRoute = Route.createInvalid(null, new DummyAttributeFactory());

        assertThat(validRoute.compareTo(invalidRoute), greaterThan(0));
    }

    @Test
    public void
    compareTo_RoutesWithEqualAttributesAndPaths_Equal() throws Exception {
        Route route1 = new Route(null, new DummyAttribute(), new PathAttribute());
        Route route2 = new Route(null, new DummyAttribute(), new PathAttribute());

        assertThat(route1.compareTo(route2), equalTo(0));
    }

    @Test
    public void
    compareTo_RouteDummyAttribute1WithRouteDummyAttribute2BothWithEmptyPaths_Greater() throws Exception {
        Route route1 = new Route(null, new DummyAttribute(1), new PathAttribute());
        Route route2 = new Route(null, new DummyAttribute(2), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteDummyAttribute2WithRouteDummyAttribute1BothWithEmptyPaths_Lesser() throws Exception {
        Route route1 = new Route(null, new DummyAttribute(2), new PathAttribute());
        Route route2 = new Route(null, new DummyAttribute(1), new PathAttribute());

        assertThat(route1.compareTo(route2), lessThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndPathWithOneNodeWithRouteWithAttr0AndEmptyPath_Greater()
            throws Exception {
        Route route1 = new Route(null, new DummyAttribute(), new PathAttribute(Factory.createNode()));
        Route route2 = new Route(null, new DummyAttribute(), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndEmptyPathWithRouteWithAttr0AndPathWithOneNode_Lesser()
            throws Exception {
        Route route1 = new Route(null, new DummyAttribute(), new PathAttribute());
        Route route2 = new Route(null, new DummyAttribute(), new PathAttribute(Factory.createNode()));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr0AndPathWithOneNodeWithRouteWithAttr1AndEmptyPath_Greater()
            throws Exception {
        Route route1 = new Route(null, new DummyAttribute(), new PathAttribute(Factory.createNode()));
        Route route2 = new Route(null, new DummyAttribute(1), new PathAttribute());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void
    compareTo_RouteWithAttr1AndEmptyPathWithRouteWithAttr0AndPathWithOneNode_Lesser()
            throws Exception {
        Route route1 = new Route(null, new DummyAttribute(1), new PathAttribute());
        Route route2 = new Route(null, new DummyAttribute(), new PathAttribute(Factory.createNode()));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

}