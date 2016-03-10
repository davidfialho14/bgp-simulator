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

        // expect invalid attribute
        Attribute expectedAttribute = ComponentFactory.createAttributeFactory().createInvalid();

        assertThat(table.getAttribute(destination, notSetNeighbour), equalTo(expectedAttribute));
    }

    // TODO clear method

}