package simulation;

import network.Factory;
import network.Node;
import org.junit.Test;
import policies.DummyAttribute;
import policies.DummyAttributeFactory;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class RouteTableTest {

    /**
     * Creates a route table. The neighbours can be added as normal parameters or by an array.
     * @param neighbours neighbours to assign to the route table.
     * @return route table initialized.
     */
    private RouteTable createRouteTable(Node... neighbours) {
        return new RouteTable(Arrays.asList(neighbours), new DummyAttributeFactory());
    }

    @Test
    public void
    setAttribute_Attr0ForTableWithNoNeighbours_GetAttributeReturnsNull() throws Exception {
        RouteTable table = createRouteTable();
        Node neighbour = Factory.createNode(1);
        Node destination = Factory.createRandomNode();

        table.setAttribute(destination, neighbour, new DummyAttribute());

        assertThat(table.getAttribute(destination, neighbour), is(nullValue()));
    }

    @Test
    public void 
    setAttribute_Attr0ForValidNeighbourAndNonExistingDestination_GetAttributeReturnsAttr0() throws Exception {
        Node validNeighbour = Factory.createRandomNode();
        Node destination = Factory.createRandomNode();
        RouteTable table = createRouteTable(validNeighbour);
        Attribute attribute = new DummyAttribute();

        table.setAttribute(destination, validNeighbour, attribute);

        assertThat(table.getAttribute(destination, validNeighbour), equalTo(attribute));
    }

    @Test
    public void
    setAttribute_Attr2ForDestinationAndNeighbourWithAttr1_GetAttributeReturnsAttr2() throws Exception {
        Node validNeighbour = Factory.createRandomNode();
        Node destination = Factory.createRandomNode();
        RouteTable table = createRouteTable(validNeighbour);
        table.setAttribute(destination, validNeighbour, new DummyAttribute(1));

        Attribute attribute = new DummyAttribute(2);
        table.setAttribute(destination, validNeighbour, attribute);

        assertThat(table.getAttribute(destination, validNeighbour), equalTo(attribute));
    }

    @Test
    public void
    setAttribute_Attr0ForInvalidNeighbour_GetAttributeReturnsNull() throws Exception {
        Node destination = Factory.createRandomNode();
        RouteTable table = createRouteTable();
        Node invalidNeighbour = Factory.createRandomNode();

        assertThat(table.getAttribute(destination, invalidNeighbour), is(nullValue()));
    }

    @Test
    public void
    getAttribute_ForExistingDestinationAndNeighbourWithoutAttrPreviouslyAssigned_ReturnsInvalidAttribute()
            throws Exception {
        Node neighbourWithAttrAssigned = Factory.createRandomNode();
        Node neighbourWithoutAttrAssigned = Factory.createRandomNode();
        RouteTable table = createRouteTable(neighbourWithAttrAssigned, neighbourWithoutAttrAssigned);
        Node destination = Factory.createRandomNode();
        table.setAttribute(destination, neighbourWithAttrAssigned, new DummyAttribute());

        assertThat(table.getAttribute(destination, neighbourWithoutAttrAssigned).isInvalid(), is(true));
    }

    @Test
    public void
    clear_TableWithOnly1Attribute_GetAttributeReturnsNull() throws Exception {
        Node neighbour = Factory.createRandomNode();
        RouteTable table = createRouteTable(neighbour);
        Node destination = Factory.createRandomNode();
        table.setAttribute(destination, neighbour, new DummyAttribute());

        table.clear();

        assertThat(table.getAttribute(destination, neighbour), is(nullValue()));
    }

    @Test
    public void
    clear_SettingAttr0ForDestination1AndValidNeighbour_GetAttributeReturnsAttr0() throws Exception {
        Node neighbour = Factory.createRandomNode();
        RouteTable table = createRouteTable(neighbour);
        Node destination = Factory.createNode(1);
        table.setAttribute(destination, neighbour, new DummyAttribute());
        Attribute attribute = new DummyAttribute(1);

        table.clear();
        table.setAttribute(destination, neighbour, attribute);

        assertThat(table.getAttribute(destination, neighbour), is(attribute));
    }

    @Test
    public void
    getSelectedRoute_ForNonExistingDestination_ReturnsNull() throws Exception {
        Node neighbour = Factory.createRandomNode();
        RouteTable table = createRouteTable(neighbour);
        Node destination = Factory.createRandomNode();

        assertThat(table.getSelectedRoute(destination, null), is(nullValue()));
    }

    private void setRoute(RouteTable table, Node destination, Node neighbour, Route route) {
        table.setAttribute(destination, neighbour, route.getAttribute());
        table.setPath(destination, neighbour, route.getPath());
    }

    @Test
    public void
    getSelectedRoute_TableWithOneNeighbourWithAttr0_ReturnsRouteWithAttr0() throws Exception {
        Node neighbour = Factory.createRandomNode();
        RouteTable table = createRouteTable(neighbour);
        Node destination = Factory.createRandomNode();
        Route route = new Route(destination, new DummyAttribute(), new PathAttribute());
        setRoute(table, destination, neighbour, route);

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void
    getSelectedRoute_TableWithNeighbourWithAttr0AndNeighbourWithoutAssignedRoute_ReturnsRouteWithAttr0()
            throws Exception {
        Node[] neighbours = Factory.createRandomNodes(2);
        RouteTable table = createRouteTable(neighbours);
        Node destination = Factory.createRandomNode();
        Route route = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, neighbours[0], route);

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void
    getSelectedRoute_TableWith2NeighboursWithAttrs0And1_ReturnsRouteWithAttr1() throws Exception {
        Node[] neighbours = Factory.createRandomNodes(2);
        RouteTable table = createRouteTable(neighbours);
        Node destination = Factory.createRandomNode();
        Route routeWithAttr0 = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, neighbours[0], routeWithAttr0);
        Route routeWithAttr1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        setRoute(table, destination, neighbours[1], routeWithAttr1);

        assertThat(table.getSelectedRoute(destination, null), is(routeWithAttr1));
    }

    @Test
    public void
    getSelectedRoute_TableWith2Neighbours0And1WithAttrs0And1AndNeighbour1IsIgnored_ReturnsRouteWithAttr0()
            throws Exception {
        Node[] neighbours = Factory.createRandomNodes(2);
        RouteTable table = createRouteTable(neighbours);
        Node destination = Factory.createRandomNode();
        Route routeWithAttr0 = new Route(destination, new DummyAttribute(0), new PathAttribute());
        setRoute(table, destination, neighbours[0], routeWithAttr0);
        Route routeWithAttr1 = new Route(destination, new DummyAttribute(1), new PathAttribute());
        setRoute(table, destination, neighbours[1], routeWithAttr1);

        assertThat(table.getSelectedRoute(destination, neighbours[1]), is(routeWithAttr0));
    }

}