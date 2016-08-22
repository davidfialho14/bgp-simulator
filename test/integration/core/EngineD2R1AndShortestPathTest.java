package core;

import core.schedulers.FIFOScheduler;
import core.topology.Node;
import factories.ShortestPathTopologyFactory;
import factories.TopologyFactory;
import org.junit.Before;
import org.junit.Test;
import protocols.D2R1Protocol;

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
public class EngineD2R1AndShortestPathTest extends SimulateEngineTest {

    private TopologyFactory factory = new ShortestPathTopologyFactory();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new D2R1Protocol();
    }

    @Test(timeout = 2000)
    public void simulate_Network0ForDestination1_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(0), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(0), sproute(0, path()), invalidRoute(),         invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 2, 1),
                destination(0), invalidRoute(), invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),
                destination(0), invalidRoute()
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(1), invalidRoute(),     sproute(1, path(1)),    invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),        splink(1, 2, 1),
                destination(1), sproute(0, path()), invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),
                destination(1), invalidRoute()
        )));
    }
    
    @Test(timeout = 2000)
    public void simulate_Network1ForDestination2_Converges() throws Exception {
        int destinationId = 2;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(2), invalidRoute(),     sproute(2, path(1, 2)), sproute(0, path(2))
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),        splink(1, 2, 1),
                destination(2), invalidRoute(),     sproute(1, path(2))
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),
                destination(2), sproute(0, path())
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network2ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(2), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is(table(
                                selfLink(0),        splink(0, 1, 1),
                destination(0), sproute(0, path()), invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is(table(
                                selfLink(1),    splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(2, path(2, 0))
        )));

        assertThat(state.get(new Node(2)).getTable(), is(table(
                                selfLink(2),    splink(2, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network3ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(3), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, -1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    splink(2, 0, 0),     splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(state.get(new Node(3)).getTable(), is( table(
                                selfLink(3),    splink(3, 0, 0),     splink(3, 1, -2),
                destination(0), invalidRoute(), sproute(0, path(0)), sproute(-2, path(1, 0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network4ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(4), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is(table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new Node(1)).getTable(), is(table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is(table(
                                selfLink(2),    splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(2, path(3, 1, 0))
        )));

        assertThat(state.get(new Node(3)).getTable(), is(table(
                                selfLink(3),    splink(3, 1, 1),
                destination(0), invalidRoute(), sproute(1, path(1, 0))
        )));
    }
}