package network;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class RouteTableTest {

    private RouteTable table;
    private Node defaultNeighbour;

    @Before
    public void setUp() throws Exception {
        defaultNeighbour = new Node(null, 0, null);
        table = new RouteTable(Collections.singletonList(defaultNeighbour), null);
    }

    @Test
    public void setAttributeForNonExistingDestination_AttributeWasSetToPairDestinationNeighbour() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node

        Attribute expectedAttribute = ComponentFactory.createAttribute(0);
        table.setAttribute(destination, defaultNeighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, defaultNeighbour), equalTo(expectedAttribute));
    }

    @Test
    public void setAttributeForExistingDestination_AttributeChanged() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node
        table.setAttribute(destination, defaultNeighbour, ComponentFactory.createAttribute(0));

        Attribute expectedAttribute = ComponentFactory.createAttribute(1);
        table.setAttribute(destination, defaultNeighbour, ComponentFactory.createAttribute(1));

        assertThat(table.getAttribute(destination, defaultNeighbour), equalTo(expectedAttribute));
    }

    // set attribute for destination that does not exist (neighbour exists)
    // set attribute for destination that exists (neighbour exists)
    // set attribute for neighbour that does not exist (destination exists)
    // TODO clear method

}