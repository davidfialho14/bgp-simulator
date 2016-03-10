package network;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RouteTableTest {

    @Test
    public void setAttributeForNonExistingDestination_AttributeWasSetToPairDestinationNeighbour() throws Exception {
        Node neighbour = new Node(null, 0, null);
        Node[] neighbours = { neighbour };
        RouteTable table = new RouteTable(Arrays.asList(neighbours), null);
        Node destination = new Node(null, 1, null); // any destination node

        Attribute expectedAttribute = ComponentFactory.createAttribute(0);
        Attribute attribute = ComponentFactory.createAttribute(0);
        table.setAttribute(destination, neighbour, attribute);

        assertThat(table.getAttribute(destination, neighbour), equalTo(expectedAttribute));
    }

    // set attribute for destination that does not exist (neighbour exists)
    // set attribute for destination that exists (neighbour exists)
    // set attribute for neighbour that does not exist (destination exists)
    // TODO clear method

}