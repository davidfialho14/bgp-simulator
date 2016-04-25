package simulation;

import network.Link;
import network.Node;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static simulation.Route.invalidRoute;
import static wrappers.DummyWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.network.NetworkWrapper.anyNode;
import static wrappers.routetable.RouteTableWrapper.table;

public class RouteTableTest {

    @Test
    public void getRoute_OnTableWithoutOutLinks_Null() throws Exception {
        assertThat(table().getRoute(anyNode(), anyDummyLink()), is(nullValue()));
    }

    @Test
    public void setRoute_WithAttr1AndEmptyPathOnTableWithoutOutLinks_GetsNull() throws Exception {
        RouteTable routeTable = table();
        Node destination = new Node(0);
        Link outLink = dummyLink(1, 2);

        routeTable.setRoute(outLink, dummyRoute(0, 1, path()));

        assertThat(routeTable.getRoute(destination, outLink), is(nullValue()));
    }

    @Test
    public void getRoute_ForOutLink1To2OnTableWithoutKnownDestinationsAndOutLinkFrom1To2_Invalid() throws Exception {
        RouteTable routeTable = table(dummyOutLink(1, 2));
        Node destination = new Node(0);

        assertThat(routeTable.getRoute(destination, dummyLink(1, 2)), is(invalidRoute(destination)));
    }

    @Test
    public void
    setRoute_FromOutLink1To2AndForDest0WithAttr1AndEmptyPath_GetsRouteWithAttr0AndEmptyPathForDest0() throws Exception {
        RouteTable routeTable = table(dummyOutLink(1, 2));
        Node destination = new Node(0);
        Link outLink = dummyLink(1, 2);

        routeTable.setRoute(outLink, dummyRoute(0, 1, path()));

        assertThat(routeTable.getRoute(destination, outLink), is(dummyRoute(0, 1, path())));
    }

    @Test
    public void
    setRoute_ForDest0WithAttr0AndEmptyPathAndAfterItChangeAttrToAttr1_GetsRouteForDest0WithAttr0AndEmptyPath()
            throws Exception {
        Node destination = new Node(0);
        Link outLink = dummyLink(1, 2);
        Route route = dummyRoute(0, 0, path());
        RouteTable routeTable = table(dummyOutLink(1, 2));

        routeTable.setRoute(outLink, route);
        route.setAttribute(dummyAttr(1));

        assertThat(routeTable.getRoute(destination, outLink), is(dummyRoute(0, 0, path())));
    }

}