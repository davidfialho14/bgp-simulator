package core;

import org.junit.Test;
import stubs.Stubs;

import static core.InvalidRoute.invalidRoute;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;

public class RouteTest {

    @Test
    public void compareTo_InvalidRouteToInvalidRoute_Equal() throws Exception {
        assertThat(invalidRoute().compareTo(invalidRoute()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidRouteToRoute0AndEmptyPath_Greater() throws Exception {
        assertThat(invalidRoute().compareTo(route(Stubs.stubAttr(0), path())), greaterThan(0));
    }

    @Test
    public void compareTo_Route0AndEmptyPathToInvalidRoute_Lesser() throws Exception {
        assertThat(route(Stubs.stubAttr(0), path()).compareTo(invalidRoute()), lessThan(0));
    }

    @Test
    public void compareTo_Route0AndEmptyPathAndRoute0AndEmptyPath_Equal() throws Exception {
        Route route1 = route(Stubs.stubAttr(0), path());
        Route route2 = route(Stubs.stubAttr(0), path());

        assertThat(route1.compareTo(route2), equalTo(0));
    }

    @Test
    public void compareTo_Route1AndEmptyPathToRoute2AndEmptyPath_Greater() throws Exception {
        Route route1 = route(Stubs.stubAttr(1), path());
        Route route2 = route(Stubs.stubAttr(2), path());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void compareTo_Route2AndEmptyPathToRoute1AndEmptyPath_Lesser() throws Exception {
        Route route1 = route(Stubs.stubAttr(1), path());
        Route route2 = route(Stubs.stubAttr(2), path());

        assertThat(route2.compareTo(route1), lessThan(0));
    }

    @Test
    public void compareTo_Route0AndPathWithNode0ToRoute0AndEmptyPath_Greater() throws Exception {
        Route route1 = route(Stubs.stubAttr(0), path(0));
        Route route2 = route(Stubs.stubAttr(0), path());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void compareTo_Route0AndEmptyPathToRoute0AndPathWithNode0_Lesser() throws Exception {
        Route route1 = route(Stubs.stubAttr(0), path());
        Route route2 = route(Stubs.stubAttr(0), path(0));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

    @Test
    public void compareTo_Route1AndPathWithNode0ToRoute2AndEmptyPath_Greater() throws Exception {
        Route route1 = route(Stubs.stubAttr(1), path(0));
        Route route2 = route(Stubs.stubAttr(2), path());

        assertThat(route1.compareTo(route2), greaterThan(0));
    }

    @Test
    public void compareTo_Route2AndEmptyPathToRoute1AndPathWithNode0_Lesser() throws Exception {
        Route route1 = route(Stubs.stubAttr(2), path());
        Route route2 = route(Stubs.stubAttr(1), path(0));

        assertThat(route1.compareTo(route2), lessThan(0));
    }

}