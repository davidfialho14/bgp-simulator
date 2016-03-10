package network;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.*;
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

    @Test
    public void setAttributeForNonExistingNeighbour_GetAttributeReturnsNull() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node
        Node nonExistingNeighbour = new Node(null, 1, null);

        table.setAttribute(destination, nonExistingNeighbour, ComponentFactory.createAttribute(0));

        assertThat(table.getAttribute(destination, defaultNeighbour), is(nullValue()));
    }

    @Test
    public void setPathForNonExistingDestination_PathWasSetToPairDestinationNeighbour() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node

        PathAttribute expectedPath = new PathAttribute();
        table.setPath(destination, defaultNeighbour, new PathAttribute());

        assertThat(table.getPath(destination, defaultNeighbour), equalTo(expectedPath));
    }

    @Test
    public void setPathForExistingDestination_PathChanged() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node
        table.setPath(destination, defaultNeighbour, new PathAttribute());

        PathAttribute expectedPath = new PathAttribute(new Node(null, 2, null)); // expect different path
        table.setPath(destination, defaultNeighbour, new PathAttribute(new Node(null, 2, null)));

        assertThat(table.getPath(destination, defaultNeighbour), equalTo(expectedPath));
    }

    @Test
    public void setPathForNonExistingNeighbour_GetPathReturnsNull() throws Exception {
        Node destination = new Node(null, 1, null); // any destination node
        Node nonExistingNeighbour = new Node(null, 1, null);

        table.setPath(destination, nonExistingNeighbour, new PathAttribute(destination));

        assertThat(table.getPath(destination, defaultNeighbour), is(nullValue()));
    }

    // TODO clear method

}