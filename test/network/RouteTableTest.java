package network;

import org.junit.Test;

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
        return new RouteTable(Arrays.asList(neighbours), ComponentFactory.createAttributeFactory());
    }

    @Test
    public void setAttributeForNonExistingDestination_AttributeWasSetToPairDestinationNeighbour() throws Exception {
        Node existingNeighbour = new Node(null, 0, null);
        Node destination = new Node(null, 1, null); // any destination node
        RouteTable table = createRouteTable(existingNeighbour);

        Attribute expectedAttribute = ComponentFactory.createAttribute(0);
        table.setAttribute(destination, existingNeighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, existingNeighbour), equalTo(expectedAttribute));
    }

    @Test
    public void setAttributeForExistingDestination_AttributeChanged() throws Exception {
        Node existingNeighbour = new Node(null, 0, null);
        Node destination = new Node(null, 1, null);
        RouteTable table = createRouteTable(existingNeighbour);
        table.setAttribute(destination, existingNeighbour, ComponentFactory.createAttribute(0));    // add destination

        Attribute expectedAttribute = ComponentFactory.createAttribute(1);
        table.setAttribute(destination, existingNeighbour, ComponentFactory.createAttribute(1));

        assertThat(table.getAttribute(destination, existingNeighbour), equalTo(expectedAttribute));
    }

    @Test
    public void setAttributeForNonExistingNeighbour_GetAttributeReturnsNull() throws Exception {
        Node existingNeighbour = new Node(null, 0, null);
        Node destination = new Node(null, 1, null);
        RouteTable table = createRouteTable(existingNeighbour);
        Node nonExistingNeighbour = new Node(null, 1, null);

        table.setAttribute(destination, nonExistingNeighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, existingNeighbour), is(nullValue()));
    }

    @Test
    public void getAttributeNotSetForExistingDestination_ReturnsInvalidAttribute() throws Exception {
        Node existingNeighbour = new Node(null, 0, null);
        Node notSetNeighbour = new Node(null, 1, null);  // neighbour for which the attribute was not yet set
        RouteTable table = createRouteTable(existingNeighbour, notSetNeighbour);
        Node destination = new Node(null, 2, null);
        table.setAttribute(destination, existingNeighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, notSetNeighbour).isInvalid(), is(true));
    }

    @Test
    public void clearTableWithOnly1AttributeSet_GetAttributeForSamePairReturnsNull() throws Exception {
        Node neighbour = new Node(null, 0, null);
        RouteTable table = createRouteTable(neighbour);
        Node destination = new Node(null, 1, null);
        table.setAttribute(destination, neighbour, ComponentFactory.createAttribute(0));

        table.clear();

        assertThat(table.getAttribute(destination, neighbour), is(nullValue()));
    }

    @Test
    public void afterClearingTableSetAttributeToExistingNeighbour_GetAttributeReturnsSetAttribute() throws Exception {
        Node neighbour = new Node(null, 0, null);
        RouteTable table = createRouteTable(neighbour);
        Node destination = new Node(null, 1, null);
        table.setAttribute(destination, neighbour, ComponentFactory.createAttribute(0));
        table.clear();

        Attribute expectedAttribute = ComponentFactory.createAttribute(0);
        table.setAttribute(destination, neighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, neighbour), is(expectedAttribute));
    }

    @Test
    public void getSelectedRouteWithNonExistingDestination_ReturnsNull() throws Exception {
        Node neighbour = new Node(null, 0, null);
        RouteTable table = createRouteTable(neighbour);
        Node destination = new Node(null, 1, null);

        assertThat(table.getSelectedRoute(destination, null), is(nullValue()));
    }

    @Test
    public void getSelectedRouteWithOnly1Neighbour_ReturnsRouteOfTheOnlyNeighbour() throws Exception {
        Node neighbour = new Node(null, 0, null);
        RouteTable table = createRouteTable(neighbour);
        Node destination = new Node(null, 1, null);

        // add a route to the table
        Route route = new Route(destination, ComponentFactory.createAttribute(0),
                new PathAttribute(new Node(null, 2, null)));
        table.setAttribute(destination, neighbour, route.getAttribute());
        table.setPath(destination, neighbour, route.getPath());

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void getSelectedRouteWith2Neighbours_ReturnsPreferredRouteOfTheTwo() throws Exception {
        Node[] neighbours = ComponentFactory.createRandomNode(2);
        RouteTable table = createRouteTable(neighbours);
        Node destination = ComponentFactory.createRandomNode();

        // add a route to the table
        Route route = new Route(destination, ComponentFactory.createAttribute(0),
                new PathAttribute(ComponentFactory.createRandomNode()));
        table.setAttribute(destination, neighbours[0], route.getAttribute());
        table.setPath(destination, neighbours[0], route.getPath());

        assertThat(table.getSelectedRoute(destination, null), is(route));
    }

    @Test
    public void getSelectedRouteIgnoringTheNeighbourWithPreferredRoute_ReturnsTheOtherRoute() throws Exception {
        Node[] neighbours = ComponentFactory.createRandomNode(2);
        RouteTable table = createRouteTable(neighbours);
        Node destination = ComponentFactory.createRandomNode();

        // add a route to the table
        Route preferredRoute = new Route(destination, ComponentFactory.createAttribute(0),
                new PathAttribute(ComponentFactory.createRandomNode()));
        table.setAttribute(destination, neighbours[0], preferredRoute.getAttribute());
        table.setPath(destination, neighbours[0], preferredRoute.getPath());

        Route otherRoute = new Route(destination, ComponentFactory.createAttribute(1),
                new PathAttribute(ComponentFactory.createRandomNode()));
        table.setAttribute(destination, neighbours[1], otherRoute.getAttribute());
        table.setPath(destination, neighbours[1], otherRoute.getPath());

        assertThat(table.getSelectedRoute(destination, neighbours[0]), is(otherRoute));
    }

}