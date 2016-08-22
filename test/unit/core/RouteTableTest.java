package core;

import core.network.Link;
import core.network.Node;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.StubWrapper.*;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

public class RouteTableTest {

    /**
     * Creates a list of links from varargs.
     */
    private static Collection<Link> links(Link... links) {
        return Arrays.asList(links);
    }

    @Test
    public void getRoute_OnTableWithoutOutLinks_Null() throws Exception {
        assertThat(table().getRoute(anyStubLink()), is(nullValue()));
    }

    @Test
    public void setRoute_WithAttr1AndEmptyPathOnTableWithoutOutLinks_GetsRouteWithAttr1AndEmptyPath() throws Exception {
        RouteTable routeTable = new RouteTable(new Node(0));
        Link outLink = stubLink(1, 2);

        routeTable.setRoute(outLink, stubRoute(0, 1, path()));

        assertThat(routeTable.getRoute(outLink), is(stubRoute(0, 1, path())));
    }

    @Test
    public void getRoute_ForOutLink1To2OnTableWithoutKnownDestinationsAndOutLinkFrom1To2_Invalid() throws Exception {
        Node destination = new Node(0);
        RouteTable routeTable = new RouteTable(destination, links(stubLink(1, 2)));

        assertThat(routeTable.getRoute(stubLink(1, 2)), is(Route.invalidRoute(destination)));
    }

    @Test
    public void
    setRoute_FromOutLink1To2AndForDest0WithAttr1AndEmptyPath_GetsRouteWithAttr0AndEmptyPathForDest0() throws Exception {
        Node destination = new Node(0);
        Link outLink = stubLink(1, 2);
        RouteTable routeTable = new RouteTable(destination, links(outLink));

        routeTable.setRoute(outLink, stubRoute(0, 1, path()));

        assertThat(routeTable.getRoute(outLink), is(stubRoute(0, 1, path())));
    }

    @Test
    public void
    setRoute_ForDest0WithAttr0AndEmptyPathAndAfterItChangeAttrToAttr1_GetsRouteForDest0WithAttr0AndEmptyPath()
            throws Exception {
        Node destination = new Node(0);
        Link outLink = stubLink(1, 2);
        RouteTable routeTable = new RouteTable(destination, links(outLink));
        Route route = stubRoute(0, 0, path());

        routeTable.setRoute(outLink, route);
        route.setAttribute(stubAttr(1));

        assertThat(routeTable.getRoute(outLink), is(stubRoute(0, 0, path())));
    }

    @Test
    public void
    setRoute_WithAttr2AndEmptyPathForOutLinkAlreadyAssignedWithRouteWithAttr1AndEmpty_GetsRouteWithAttr2AndEmptyPath()
            throws Exception {
        Node destination = new Node(0);
        Link outLink = stubLink(0, 1);
        RouteTable routeTable = new RouteTable(destination, links(outLink));
        routeTable.setRoute(outLink, stubRoute(0, 1, path()));

        routeTable.setRoute(outLink, stubRoute(0, 2, path()));

        assertThat(routeTable.getRoute(outLink), is(stubRoute(0, 2, path())));
    }

    @Test
    public void
    getRoute_ForDestination0AndOutLink1To2ButTheRouteForDestination0WasOnlySetForOutLink1To3_InvalidRoute()
            throws Exception {
        Node destination = new Node(0);
        Link outLink1To2 = stubLink(1, 2);
        Link outLink1To3 = stubLink(1, 3);
        RouteTable routeTable = new RouteTable(destination, links(outLink1To2, outLink1To3));

        routeTable.setRoute(outLink1To2, stubRoute(0, 0, path()));  // assigned attribute to other link

        assertThat(routeTable.getRoute(outLink1To3), is(Route.invalidRoute(destination)));
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
        RouteTable routeTable = table(stubOutLink(0, 1));

        routeTable.clear();

        assertThat(routeTable, is(table(stubOutLink(0, 1))));
    }

    @Test
    public void
    clear_TableWithOutLinkBetween0And1AndKnownDestinations0And1_EmptyTableWithOutLinkBetween0And1() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),
                destination(0), stubRoute(0, path())
        );

        routeTable.clear();

        assertThat(routeTable, is(table(destination(0), stubOutLink(0, 1))));
    }

    @Test
    public void removeOutLink_From0To1OnEmptyTable_EmptyTable() throws Exception {
        RouteTable routeTable = table();

        routeTable.removeOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table()));
    }

    @Test
    public void removeOutLink_From0To1OnTableWithOutLink2To3_TableWithOutLink2To3() throws Exception {
        RouteTable routeTable = table(stubOutLink(2, 3));

        routeTable.removeOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table(stubOutLink(2, 3))));
    }

    @Test
    public void removeOutLink_From0To1OnTableWithOutLink0To1_EmptyTable() throws Exception {
        RouteTable routeTable = table(stubOutLink(0, 1));

        routeTable.removeOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table()));
    }

    @Test
    public void removeOutLink_From0To1OnTableWithOutLink0To1AndOutLink0To2_TableWithOutLink0To2() throws Exception {
        RouteTable routeTable = table(stubOutLink(0, 1), stubOutLink(0, 2));

        routeTable.removeOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table(stubOutLink(0, 2))));
    }

    @Test
    public void
    removeOutLink_From0To1OnTableWithOneKnownDestination_RoutesAreKeptForOtherOutLink() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  stubRoute(0, path())
        );

        routeTable.removeOutLink(stubLink(0, 2));

        assertThat(routeTable, is(table(
                                stubOutLink(0, 1),
                destination(0), stubRoute(0, path())
        )));
    }

    @Test
    public void
    removeOutLink_From0To1WithAssignedRouteForDestination0_GetRouteForOutLink0To1ReturnsNull() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  stubRoute(0, path())
        );
        Link removedOutLink = stubLink(0, 1);
        Node destination = new Node(0);

        routeTable.removeOutLink(removedOutLink);

        assertThat(routeTable.getRoute(removedOutLink), is(nullValue()));
    }

    @Test
    public void addOutLink_From0To1ToEmptyTable_TableWithOutLink0To1() throws Exception {
        RouteTable routeTable = table();

        routeTable.addOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table(stubOutLink(0, 1))));
    }

    @Test
    public void addOutLink_From0To1ToTableWithOutLink2To3_TableWithOutLinks0To1And2To3() throws Exception {
        RouteTable routeTable = table(stubOutLink(2, 3));

        routeTable.addOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table(stubOutLink(0, 1), stubOutLink(2, 3))));
    }

    @Test
    public void addOutLink_From0To1ToTableWithOutLink0To1_TableWithOutLink0To1() throws Exception {
        RouteTable routeTable = table(stubOutLink(0, 1));

        routeTable.addOutLink(stubLink(0, 1));

        assertThat(routeTable, is(table(stubOutLink(0, 1))));
    }

    @Test
    public void
    addOutLink_From0To1ToTableAlreadyWithOutLink0To1WithRoute0AndEmptyPathForDestination0_GetsRoute0AndEmptyPath()
            throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  stubRoute(0, path())
        );
        Node destination = new Node(0);
        Link addedOutLink = stubLink(0, 1);

        routeTable.addOutLink(addedOutLink);

        assertThat(routeTable.getRoute(addedOutLink), is(stubRoute(0, 0, path())));
    }

    @Test
    public void addOutLink_From0To1ToTableWithDestination0_GetsInvalidRouteForOutLink0To1() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 2),
                destination(0), stubRoute(0, path())
        );
        Link addedOutLink = stubLink(0, 1);
        Node destination = new Node(0);

        routeTable.addOutLink(addedOutLink);

        assertThat(routeTable.getRoute(addedOutLink), is(Route.invalidRoute(destination)));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0OnEmptyTable_Null() throws Exception {
        Node destination0 = new Node(0);
        RouteTable routeTable = new RouteTable(destination0);

        assertThat(routeTable.getSelectedRoute(anyStubLink()), is(nullValue()));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0OnTableWithoutKnownDestinations_InvalidRoute() throws Exception {
        Node destination0 = new Node(0);
        RouteTable routeTable = new RouteTable(destination0, links(stubLink(0, 1)));

        assertThat(routeTable.getSelectedRoute(), is(Route.invalidRoute(destination0)));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithOnlyOneOutLink_TheOnlyRouteAvailable() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),
                destination(0), stubRoute(0, path())
        );

        assertThat(routeTable.getSelectedRoute(), is(route(0, stubAttr(0), path())));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithRoute0AndEmptyPathAndRoute1AndEmptyPath_Route1AndEmptyPath()
            throws Exception {
        RouteTable routeTable = table(
                stubOutLink(0, 1),    stubOutLink(0, 2),
                destination(0), stubRoute(0, path()), stubRoute(1, path())
        );

        assertThat(routeTable.getSelectedRoute(), is(route(0, stubAttr(1), path())));
    }

    @Test
    public void
    getSelectedRoute_ForDestination0WithRoute0AndEmptyPathAndInvalidRoute_Route0AndEmptyPath() throws Exception {
        RouteTable routeTable = table(
                stubOutLink(0, 1),    stubOutLink(0, 2),
                destination(0), stubRoute(0, path()), invalidRoute()
        );

        assertThat(routeTable.getSelectedRoute(), is(route(0, stubAttr(0), path())));
    }

    @Test
    public void getSelectedRoute_ForDestination0WithTwoInvalidsRoutes_InvalidRoute() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1), stubOutLink(0, 2),
                destination(0), invalidRoute(),     invalidRoute()
        );

        assertThat(routeTable.getSelectedRoute(), is(Route.invalidRoute(new Node(0))));
    }

    @Test
    public void
    getSelectedRoute_IgnoringOutLink0To2ForDestination0WithRoute0ForOutLink0To1AndRoute1ForOutLink0To2_Route0()
            throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  stubRoute(1, path())
        );

        assertThat(routeTable.getSelectedRoute(stubLink(0, 2)), is(route(0, stubAttr(0), path())));
    }

    @Test
    public void
    getSelectedRoute_IgnoringOutLink0To1ForDestination0OnTableWithOnlyOutLink0To1_Null() throws Exception {
        RouteTable routeTable = table(
                                stubOutLink(0, 1),
                destination(0), stubRoute(0, path())
        );

        assertThat(routeTable.getSelectedRoute(stubLink(0, 1)), is(nullValue()));
    }

    @Test
    public void
    getSelectedRoute_ForOutLinkWithImplicitInvalidRoute_InvalidRoute() throws Exception {
        RouteTable routeTable = table(destination(0), stubOutLink(0, 1), stubOutLink(0, 2));
        routeTable.setRoute(stubLink(0, 1), route(0, stubAttr(0), path()));
        Node destination = new Node(0);

        assertThat(routeTable.getSelectedRoute(stubLink(0, 1)), is(Route.invalidRoute(destination)));
    }


    @Test
    public void
    equals_RouteTablesWithExplicitInvalidRouteAndWithExplicitInvalidRoute_AreEqual() throws Exception {
        RouteTable routeTable1 = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  invalidRoute()
        );
        RouteTable routeTable2 = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  invalidRoute()
        );

        assertThat(routeTable1, is(equalTo(routeTable2)));
    }

    @Test
    public void
    equals_RouteTablesWithExplicitInvalidRouteAndWithImplicitInvalidRoute_AreEqual() throws Exception {
        RouteTable routeTable1 = table(
                                stubOutLink(0, 1),     stubOutLink(0, 2),
                destination(0), stubRoute(0, path()),  invalidRoute()
        );
        RouteTable routeTable2 = table(destination(0), stubOutLink(0, 1), stubOutLink(0, 2));
        routeTable2.setRoute(stubLink(0, 1), route(0, stubAttr(0), path()));

        assertThat(routeTable1, is(equalTo(routeTable2)));
    }

    @Test
    public void
    equals_TableWithOutLink0To2AndTableWithOutLink0To1_AreEqual() throws Exception {
        RouteTable routeTable1 = table(
                                stubOutLink(0, 2),
                destination(0), stubRoute(0, path())
        );
        RouteTable routeTable2 = table(
                                stubOutLink(0, 1),
                destination(0), stubRoute(0, path())
        );

        assertThat(routeTable1, not(equalTo(routeTable2)));
    }

    @Test
    public void
    equals_WithOutLinks0To1And0To2AndTableWithOutLink0To2_AreNotEqual() throws Exception {
        RouteTable routeTable1 = table(
                                stubOutLink(0, 2),      stubOutLink(0, 1),
                destination(0), stubRoute(0, path()),   stubRoute(0, path())
        );
        RouteTable routeTable2 = table(
                                stubOutLink(0, 2),
                destination(0), stubRoute(0, path())
        );

        assertThat(routeTable1, not(equalTo(routeTable2)));
    }

    @Test
    public void
    equals_TableWithOutLinks0To2AndTableWithOutLinks0To1And0To2_AreNotEqual() throws Exception {
        RouteTable routeTable1 = table(
                stubOutLink(0, 2),
                destination(0), stubRoute(0, path())
        );
        RouteTable routeTable2 = table(
                stubOutLink(0, 2),      stubOutLink(0, 1),
                destination(0), stubRoute(0, path()),   stubRoute(0, path())
        );


        assertThat(routeTable1, not(equalTo(routeTable2)));
    }

}