package networks;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import protocols.implementations.D1R1Protocol;
import protocols.implementations.D2R1Protocol;
import simulation.Engine;
import simulation.RouteTable;
import simulation.State;
import simulation.implementations.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.ShortestPathWrapper.*;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

public class ShortestPathNetwork6Test extends ShortestPathNetworkTest {

    private Engine engine;
    private Network network;

    @Before
    public void setUp() throws Exception {
        network = network(new ShortestPathPolicy(),
                link(from(1), to(0), label(0)),
                link(from(2), to(0), label(5)),
                link(from(3), to(1), label(1)),
                link(from(3), to(2), label(5)),
                link(from(3), to(4), label(1)),
                link(from(4), to(5), label(1)),
                link(from(5), to(3), label(1)));
    }

    /**
     * Route tables for each node when using the BGP protocol and there is no change in the network.
     */
    private static RouteTable[] BGPProtocolExpectedTables = {
            table(
                                    selfLink(0),
                    destination(0), sproute(0, path())
            ),
            table(
                                    selfLink(1),    splink(1, 0, 0),
                    destination(0), invalidRoute(), sproute(0, path(0))
            ),
            table(
                                    selfLink(2),    splink(2, 0, 5),
                    destination(0), invalidRoute(), sproute(5, path(0))
            ),
            table(
                                    selfLink(3),    splink(3, 1, 1),        splink(3, 2, 5),         splink(3, 4, 1),
                    destination(0), invalidRoute(), sproute(1, path(1, 0)), sproute(10, path(2, 0)), invalidRoute()
            ),
            table(
                                    selfLink(4),    splink(4, 5, 1),
                    destination(0), invalidRoute(), sproute(3, path(5, 3, 1, 0))
            ),
            table(
                                    selfLink(5),    splink(5, 3, 1),
                    destination(0), invalidRoute(), sproute(2, path(3, 1, 0))
            )
    };

    /**
     * Route tables for each node when using the BGP protocol and the link 3->1 is broken
     */
    private static RouteTable[] BGPProtocolAndBrokenLink3To1ExpectedTables = {
            table(
                                    selfLink(0),
                    destination(0), sproute(0, path())
            ),
            table(
                                    selfLink(1),    splink(1, 0, 0),
                    destination(0), invalidRoute(), sproute(0, path(0))
            ),
            table(
                                    selfLink(2),    splink(2, 0, 5),
                    destination(0), invalidRoute(), sproute(5, path(0))
            ),
            table(
                                    selfLink(3),    splink(3, 2, 5),         splink(3, 4, 1),
                    destination(0), invalidRoute(), sproute(10, path(2, 0)), invalidRoute()
            ),
            table(
                                    selfLink(4),    splink(4, 5, 1),
                    destination(0), invalidRoute(), sproute(12, path(5, 3, 2, 0))
            ),
            table(
                                    selfLink(5),    splink(5, 3, 1),
                    destination(0), invalidRoute(), sproute(11, path(3, 2, 0))
            )
    };

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Converges() throws Exception {
        engine = new Engine(new FIFOScheduler());
        State state = State.create(network, new BGPProtocol());

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_ConvergesToSameRouteTablesAsWithBGPProtocol() throws Exception {
        engine = new Engine(new FIFOScheduler());
        State state = State.create(network, new D1R1Protocol());

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        // TODO Event Handler
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
//        engine = new Engine(new FIFOScheduler());
//                .eventHandler(eventHandler)
//                .build();
//
//        engine.simulate(network, new D1R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(0));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant3_Converges() throws Exception {
        // TODO Link Breaker
//        engine = new Engine(new FIFOScheduler());
//                .linkBreaker(new FixedLinkBreaker(new Link(3, 1, splabel(1)), 2L))
//                .build();
//
//        engine.simulate(network, new D1R1Protocol(), 0);
//
//        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[0]));
//        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[1]));
//        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[2]));
//        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[3]));
//        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[4]));
//        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant3_DetectsOnce() throws Exception {
        // TODO Event Handler
        // TODO Link Breaker
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
//        engine = new Engine(new FIFOScheduler());
//                .linkBreaker(new FixedLinkBreaker(new Link(3, 1, splabel(1)), 2L))
//                .eventHandler(eventHandler)
//                .build();
//
//        engine.simulate(network, new D1R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(1));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_ConvergesToSameRouteTablesAsWithBGPProtocol() throws Exception {
        engine = new Engine(new FIFOScheduler());
        State state = State.create(network, new D2R1Protocol());

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        // TODO Event Handler
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
//        engine = new Engine(new FIFOScheduler());
//                .eventHandler(eventHandler)
//                .build();
//
//        engine.simulate(network, new D2R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(0));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant3_Converges() throws Exception {
        // TODO Link Breaker
//        engine = new Engine(new FIFOScheduler());
//                .linkBreaker(new FixedLinkBreaker(new Link(3, 1, splabel(1)), 2L))
//                .build();
//
//        engine.simulate(network, new D2R1Protocol(), 0);
//
//        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[0]));
//        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[1]));
//        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[2]));
//        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[3]));
//        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[4]));
//        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant3_NeverDetects() throws Exception {
        // TODO Event Handler
        // TODO Link Breaker
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
//        engine = new Engine(new FIFOScheduler());
//                .linkBreaker(new FixedLinkBreaker(new Link(3, 1, splabel(1)), 2L))
//                .eventHandler(eventHandler)
//                .build();
//
//        engine.simulate(network, new D2R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(0));
    }
}
