package core;

import factories.NetworkFactory;
import factories.ShortestPathNetworkFactory;
import core.topology.Node;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import protocols.BGPProtocol;
import core.schedulers.FIFOScheduler;

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
    Allow duplicates in order to make the tests easier to understand without having to look for the core.topology or
    the expected tables elsewhere.
 */
@SuppressWarnings("Duplicates")
public class EngineBGPAndShortestPathTest extends SimulateEngineTest {

    private NetworkFactory factory = new ShortestPathNetworkFactory();
    
    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new BGPProtocol();
    }

    @Test(timeout = 2000)
    public void simulate_Network0_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(0), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(1), destinationId, protocol);

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

    @Test//(timeout = 2000)
    public void simulate_Network1ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        core.State state = core.State.create(factory.network(1), destinationId, protocol);

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
        core.State state = core.State.create(factory.network(1), destinationId, protocol);

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
    public void simulate_Network2_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(2), destinationId, protocol);

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
    @Ignore
    public void simulate_Network3_DoesNotConverge() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(3), destinationId, protocol);

        engine.simulate(state);
    }

    @Test(timeout = 2000)
    public void simulate_Network4_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(4), destinationId, protocol);

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

    @Test(timeout = 2000)
    public void simulate_Network5_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.network(5), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    splink(2, 1, 1),
                destination(0), invalidRoute(), sproute(2, path(1, 0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network5BreakingLink2To1OnInstant1_Converges() throws Exception {
        // TODO Link Breaker
//        engine.setLinkBreaker(new FixedLinkBreaker(new Link(2, 1, new ShortestPathLabel(1)), 1L));
//
//        Network factory.core.topology(0) = core.topology(new ShortestPathPolicy(),
//                link(from(2), to(1), label(1)),
//                link(from(1), to(0), label(1)));
//
//        engine.simulate(factory.core.topology(0), protocol, 0);
//
//        assertThat(state.get(new Node(0)).getTable(), is( table(
//                                selfLink(0),
//                destination(0), sproute(0, path())
//        )));
//
//        assertThat(state.get(new Node(1)).getTable(), is( table(
//                                selfLink(1),    splink(1, 0, 1),
//                destination(0), invalidRoute(), sproute(1, path(0))
//        )));
//
//        assertThat(state.get(new Node(2)).getTable(), is( table(
//                                selfLink(2),
//                destination(0), invalidRoute()
//        )));
    }
}