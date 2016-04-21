package simulation;

import network.Link;
import network.Node;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static policies.InvalidAttribute.invalid;
import static wrappers.DummyWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.network.NetworkWrapper.anyNode;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

public class RouteTableTest {

    private Node destNode = anyNode();

    @Test
    public void setAttribute_Attr0OnTableWithoutOutLinks_GetsNull() throws Exception {
        RouteTable routeTable = table();
        Link outLink = anyDummyLink();

        routeTable.setAttribute(destNode, outLink, dummyAttr(0));

        assertThat(routeTable.getAttribute(destNode, outLink), is(nullValue()));
    }

    @Test
    public void setAttribute_Attribute0ForOutLinkBetweenNodes0And1_GetsAttribute0() throws Exception {
        RouteTable routeTable = table(dummyOutLink(0, 1));

        routeTable.setAttribute(destNode, dummyLink(0, 1), dummyAttr(0));

        assertThat(routeTable.getAttribute(destNode, dummyLink(0, 1)), is(dummyAttr(0)));
    }

    @Test
    public void setAttribute_Attr2ForOutLinkWithAttribute1AlreadyAssigned_GetsAttribute2() throws Exception {
        RouteTable routeTable = table(dummyOutLink(0, 1));
        Link outLink = dummyLink(0, 1);
        routeTable.setAttribute(destNode, outLink, dummyAttr(1));

        routeTable.setAttribute(destNode, outLink, dummyAttr(2));

        assertThat(routeTable.getAttribute(destNode, outLink), is(dummyAttr(2)));
    }

    @Test
    public void
    setAttribute_Attribute0ForOutLinkBetween0And1OnTableWithOutLinkBetween0And2_GetsNull() throws Exception {
        RouteTable routeTable = table(dummyOutLink(0, 2));
        Link outLink = dummyLink(0, 1);

        routeTable.setAttribute(destNode, outLink, dummyAttr(2));

        assertThat(routeTable.getAttribute(destNode, outLink), is(nullValue()));
    }

    @Test
    public void
    getAttribute_ForDestination1OnTableWithOnlyKnownDestination0_GetsNull() throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),
                destination(0), dummyRoute(0, path())
        );
        Node destination1 = new Node(1);

        assertThat(routeTable.getAttribute(destination1, dummyLink(0, 1)), is(nullValue()));
    }

    @Test
    public void
    getAttribute_ForDestination0AndOutLinkBetween0And1WithoutAttributePreviouslyAssigned_GetsInvalidAttribute()
            throws Exception {
        RouteTable table = table(dummyOutLink(0, 1), dummyOutLink(0, 2));
        table.setAttribute(destNode, dummyLink(0, 2), anyDummyAttr());  // assigne attribute to other link

        assertThat(table.getAttribute(destNode, dummyLink(0, 1)), is(invalid()));
    }

    @Test
    public void
    clear_EmptyTableWithoutOutLinks_TableStaysEmptyWithoutOutLinks() throws Exception {
        RouteTable routeTable = table();

        routeTable.clear();

        assertThat(routeTable, is(table()));
    }

    @Test
    public void
    clear_EmptyTableWithOutLinkBetween0And1_TableStaysEmptyWithOutLinkBetween0And1() throws Exception {
        RouteTable routeTable = table(dummyOutLink(0, 1));

        routeTable.clear();

        assertThat(routeTable, is(table(dummyOutLink(0, 1))));
    }

    @Test
    public void
    clear_TableWithOutLinkBetween0And1AndKnownDestinations0And1_EmptyTableWithOutLinkBetween0And1() throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),
                destination(0), dummyRoute(0, path()),
                destination(1), dummyRoute(0, path())
        );

        routeTable.clear();

        assertThat(routeTable, is(table(dummyOutLink(0, 1))));
    }


    @Test
    public void
    getSelectedRoute_ForDestination0OnTableWithoutKnownDestinations_GetsNull() throws Exception {
        RouteTable routeTable = table(dummyOutLink(0, 1));
        Node destination0 = new Node(0);

        assertThat(routeTable.getSelectedRoute(destination0, dummyLink(0, 1)), is(nullValue()));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithOnlyOnePossibility_GetsTheOnlyRouteAvailable() throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),
                destination(0), dummyRoute(0, path())
        );

        assertThat(routeTable.getSelectedRoute(new Node(0)), is(route(0, dummyAttr(0), path())));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithRoute0AndEmptyPathAndRoute1AndEmptyPath_GetsRoute1AndEmptyPath()
            throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),    dummyOutLink(0, 2),
                destination(0), dummyRoute(0, path()), dummyRoute(1, path())
        );

        assertThat(routeTable.getSelectedRoute(new Node(0)), is(route(0, dummyAttr(1), path())));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithRoute0AndEmptyPathAndInvalidRoute_GetsRoute0AndEmptyPath() throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),    dummyOutLink(0, 2),
                destination(0), dummyRoute(0, path()), invalidRoute()
        );

        assertThat(routeTable.getSelectedRoute(new Node(0)), is(route(0, dummyAttr(0), path())));
    }

    @Test
    public void getSelectedRoute_ForDestination0WithTwoInvalidsRoutes_GetsInvalidRoute() throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1), dummyOutLink(0, 2),
                destination(0), invalidRoute(),     invalidRoute()
        );

        assertThat(routeTable.getSelectedRoute(new Node(0)), is(Route.createInvalid(new Node(0))));
    }

    @Test
    public void
    getSelectedRoute_IgnoringOutLink2ForDestination0WithRoute0ForOutLink1AndRoute1ForOutLink2_GetsRoute0()
            throws Exception {
        RouteTable routeTable = table(
                                dummyOutLink(0, 1),     dummyOutLink(0, 2),
                destination(0), dummyRoute(0, path()),  dummyRoute(1, path())
        );

        assertThat(routeTable.getSelectedRoute(new Node(0), dummyLink(0, 2)), is(route(0, dummyAttr(0), path())));
    }

}