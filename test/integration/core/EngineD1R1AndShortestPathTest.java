package core;

import core.schedulers.FIFOScheduler;
import core.topology.ConnectedNode;
import factories.ShortestPathTopologyFactory;
import factories.TopologyFactory;
import org.junit.Before;
import org.junit.Test;
import protocols.D1R1Protocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.ShortestPathWrapper.splink;
import static wrappers.ShortestPathWrapper.sproute;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

/*
    Allow duplicates in order to make the tests easier to understand without having to look for the topology or
    the expected tables elsewhere.
 */
@SuppressWarnings("Duplicates")
public class EngineD1R1AndShortestPathTest extends SimulateEngineTest {

    private TopologyFactory factory = new ShortestPathTopologyFactory();
    
    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new D1R1Protocol();
    }

    @Test//(timeout = 2000)
    public void simulate_Topology3_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(3), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, -1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    splink(2, 0, 0),     splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(3)).getTable(), is( table(
                                selfLink(3),    splink(3, 0, 0),     splink(3, 1, -2),
                destination(0), invalidRoute(), sproute(0, path(0)), sproute(-2, path(1, 0))
        )));
    }
}