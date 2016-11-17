package protocols;

import core.NodeState;
import core.Route;
import core.RouteTable;
import core.topology.Link;
import core.topology.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static stubs.Stubs.stubLink;
import static stubs.Stubs.stubRoute;


@RunWith(Parameterized.class)
public class NeighborDetectionTest {

    private Detection detection;

    @Mock
    private RouteTable stubRouteTable;
    @InjectMocks
    private NodeState nodeState = new NodeState(stubRouteTable, null);

    private int learnedAttribute;
    private int alternativeAttribute;
    private int neighbor;               // neighbor from which the new route was learned
    private int previousNeighbor;       // previously selected neighbor
    private boolean expectedResult;

    @Before
    public void setUp() throws Exception {
        detection = new NeighborDetection();
    }

    public NeighborDetectionTest(int learnedAttribute, int alternativeAttribute, int neighbor, int previousNeighbor,
                                 boolean expectedResult) {
        MockitoAnnotations.initMocks(this);
        this.learnedAttribute = learnedAttribute;
        this.alternativeAttribute = alternativeAttribute;
        this.neighbor = neighbor;
        this.previousNeighbor = previousNeighbor;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "learned({0}) alternative({1}) prevNeighbor({2}) neighbor({3}) returns {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1, 0, 1, 1, true },
                { 1, 1, 1, 1, false },
                { 0, 1, 1, 1, false },
                { 1, 0, 2, 1, false },
        });
    }

    @Test
    public void isPolicyDispute() throws Exception {
        Node destination = Node.newNode(0); // destination node for the routes
        Link link = stubLink(1, neighbor);
        Route learnedRoute = stubRoute(destination, learnedAttribute, null);
        Route alternativeRoute = stubRoute(destination, alternativeAttribute, null);
        when(stubRouteTable.getSelectedNeighbour()).thenReturn(Node.newNode(previousNeighbor));

        assertThat(detection.isPolicyDispute(link, learnedRoute, alternativeRoute, nodeState), is(expectedResult));
    }

}