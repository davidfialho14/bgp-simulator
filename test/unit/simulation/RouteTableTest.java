package simulation;

import dummies.DummyAttribute;
import network.Link;
import network.Node;
import org.junit.Test;
import policies.Attribute;

import java.util.Arrays;

import static network.Factory.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RouteTableTest {

    private Node destination = createRandomNode();

    /**
     * Creates a route table. The out-links can be added as normal parameters or by an array.
     * @param outLinks out-links to assign to the route table.
     * @return route table initialized.
     */
    private RouteTable createRouteTable(Link... outLinks) {
        return new RouteTable(Arrays.asList(outLinks));
    }

    @Test
    public void
    setAttribute_Attr0ForTableWithNoOutLinks_GetAttributeReturnsNull() throws Exception {
        RouteTable table = createRouteTable();
        Link outLink = createRandomLink();

        table.setAttribute(destination, outLink, new DummyAttribute());

        assertThat(table.getAttribute(destination, outLink), is(nullValue()));
    }

    @Test
    public void 
    setAttribute_Attr0ForValidOutLinkAndNonExistingDestination_GetAttributeReturnsAttr0() throws Exception {
        Link validOutLink = createRandomLink();
        RouteTable table = createRouteTable(validOutLink);
        Attribute attribute = new DummyAttribute();

        table.setAttribute(destination, validOutLink, attribute);

        assertThat(table.getAttribute(destination, validOutLink), equalTo(attribute));
    }

    @Test
    public void
    setAttribute_Attr2ForDestinationAndOutLinkWithAttr1_GetAttributeReturnsAttr2() throws Exception {
        Link validOutLink = createRandomLink();
        RouteTable table = createRouteTable(validOutLink);
        table.setAttribute(destination, validOutLink, new DummyAttribute(1));

        Attribute attribute = new DummyAttribute(2);
        table.setAttribute(destination, validOutLink, attribute);

        assertThat(table.getAttribute(destination, validOutLink), equalTo(attribute));
    }

    @Test
    public void
    setAttribute_Attr0ForInvalidOutLink_GetAttributeReturnsNull() throws Exception {
        RouteTable table = createRouteTable();
        Link invalidOutLink = createRandomLink();

        assertThat(table.getAttribute(destination, invalidOutLink), is(nullValue()));
    }

    @Test
    public void
    getAttribute_ForExistingDestinationAndOutLinkWithoutAttrPreviouslyAssigned_ReturnsInvalidAttribute()
            throws Exception {
        Link outLinkWithAttrAssigned = createRandomLink();
        Link outLinkWithoutAttrAssigned = createRandomLink();
        RouteTable table = createRouteTable(outLinkWithAttrAssigned, outLinkWithoutAttrAssigned);
        table.setAttribute(destination, outLinkWithAttrAssigned, new DummyAttribute());

        assertThat(table.getAttribute(destination, outLinkWithoutAttrAssigned).isInvalid(), is(true));
    }

    @Test
    public void
    clear_TableWithOnly1Attribute_GetAttributeReturnsNull() throws Exception {
        Link outLink = createRandomLink();
        RouteTable table = createRouteTable(outLink);
        table.setAttribute(destination, outLink, new DummyAttribute());

        table.clear();

        assertThat(table.getAttribute(destination, outLink), is(nullValue()));
    }

    @Test
    public void
    clear_SettingAttr0ForDestination1AndValidOutLink_GetAttributeReturnsAttr0() throws Exception {
        Link outLink = createRandomLink();
        RouteTable table = createRouteTable(outLink);
        Node destination = new Node(1);
        table.setAttribute(destination, outLink, new DummyAttribute());
        Attribute attribute = new DummyAttribute(1);

        table.clear();
        table.setAttribute(destination, outLink, attribute);

        assertThat(table.getAttribute(destination, outLink), is(attribute));
    }

    @Test
    public void
    getSelectedRoute_ForNonExistingDestination_ReturnsNull() throws Exception {
        Link outLink = createRandomLink();
        RouteTable table = createRouteTable(outLink);

        assertThat(table.getSelectedRoute(destination, null), is(nullValue()));
    }

    private void setRoute(RouteTable table, Node destination, Link outLink, Route route) {
        table.setAttribute(destination, outLink, route.getAttribute());
        table.setPath(destination, outLink, route.getPath());
    }

    @Test
    public void
    getSelectedRoute_TableWithOneOutLinkWithAttr0_ReturnsRouteWithAttr0() throws Exception {
        Link outLink = createRandomLink();
        RouteTable table = createRouteTable(outLink);
        Route route = new Route(destination, new DummyAttribute(), new PathAttribute());
        setRoute(table, destination, outLink, route);

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void
    getSelectedRoute_TableWithOutLinkWithAttr0AndOutLinkWithoutAssignedRoute_ReturnsRouteWithAttr0()
            throws Exception {
        Link[] outLinks = createRandomLinks(2);
        RouteTable table = createRouteTable(outLinks);
        Route route = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, outLinks[0], route);

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void
    getSelectedRoute_TableWith2OutLinksWithAttrs0And1_ReturnsRouteWithAttr1() throws Exception {
        Link[] outLinks = createRandomLinks(2);
        RouteTable table = createRouteTable(outLinks);
        Route routeWithAttr0 = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, outLinks[0], routeWithAttr0);
        Route routeWithAttr1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        setRoute(table, destination, outLinks[1], routeWithAttr1);

        assertThat(table.getSelectedRoute(destination, null), is(routeWithAttr1));
    }

    @Test
    public void
    getSelectedRoute_TableWith2OutLinks0And1WithAttrs0And1AndOutLink1IsIgnored_ReturnsRouteWithAttr0()
            throws Exception {
        Link[] outLinks = createRandomLinks(2);
        RouteTable table = createRouteTable(outLinks);
        Route routeWithAttr0 = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, outLinks[0], routeWithAttr0);
        Route routeWithAttr1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        setRoute(table, destination, outLinks[1], routeWithAttr1);

        assertThat(table.getSelectedRoute(destination, outLinks[1]), is(routeWithAttr0));
    }

}