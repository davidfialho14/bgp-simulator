package core;

import addons.eventhandlers.DebugEventHandler;
import core.schedulers.FIFOScheduler;
import core.topology.ConnectedNode;
import factories.ShortestPathTopologyFactory;
import factories.TopologyFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import protocols.D2R1Protocol;

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

    @Rule
    public ErrorCollector collector = new ErrorCollector();
    private TopologyFactory factory = new ShortestPathTopologyFactory();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new D2R1Protocol();
    }

    @Test
    public void simulate_Network0ForDestination1_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(0), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));
    }

    @Test
    public void simulate_Network1ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(0), sproute(0, path()), invalidRoute(),         invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 2, 1),
                destination(0), invalidRoute(), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),
                destination(0), invalidRoute()
        )));
    }

    @Test
    public void simulate_Network1ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(1), invalidRoute(),     sproute(1, path(1)),    invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),        splink(1, 2, 1),
                destination(1), sproute(0, path()), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),
                destination(1), invalidRoute()
        )));
    }
    
    @Test
    public void simulate_Network1ForDestination2_Converges() throws Exception {
        int destinationId = 2;
        State state = State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(2), invalidRoute(),     sproute(2, path(1, 2)), sproute(0, path(2))
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),        splink(1, 2, 1),
                destination(2), invalidRoute(),     sproute(1, path(2))
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),
                destination(2), sproute(0, path())
        )));
    }

    @Test
    public void simulate_Network2ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(2), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is(table(
                                selfLink(0),        splink(0, 1, 1),
                destination(0), sproute(0, path()), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is(table(
                                selfLink(1),    splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(2, path(2, 0))
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is(table(
                                selfLink(2),    splink(2, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));
    }

    @Test
    public void simulate_Network3ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(3), destinationId, protocol);
        new DebugEventHandler(true).register(engine.getEventGenerator());

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, -1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    splink(2, 0, 0),     splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(3)).getTable(), is( table(
                                selfLink(3),    splink(3, 0, 0),     splink(3, 1, -2),
                destination(0), invalidRoute(), sproute(0, path(0)), sproute(-2, path(1, 0))
        )));
    }

    @Test
    public void simulate_Network4ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        State state = State.create(factory.topology(4), destinationId, protocol);

        engine.simulate(state);

        collector.checkThat(state.get(new ConnectedNode(0)).getTable(), is(table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        collector.checkThat(state.get(new ConnectedNode(1)).getTable(), is(table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        collector.checkThat(state.get(new ConnectedNode(2)).getTable(), is(table(
                                selfLink(2),    splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(2, path(3, 1, 0))
        )));

        collector.checkThat(state.get(new ConnectedNode(3)).getTable(), is(table(
                                selfLink(3),    splink(3, 1, 1),
                destination(0), invalidRoute(), sproute(1, path(1, 0))
        )));
    }
}