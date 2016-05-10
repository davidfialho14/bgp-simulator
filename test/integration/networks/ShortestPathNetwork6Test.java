package networks;

import addons.eventhandlers.MessageAndDetectionCountHandler;
import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import policies.shortestpath.ShortestPathPolicy;
import protocols.BGPProtocol;
import protocols.D1R1Protocol;
import protocols.D2R1Protocol;
import protocols.Protocol;
import simulation.Engine;
import simulation.RouteTable;
import simulation.State;
import simulation.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
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
    private int destinationId = 0;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
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

    /**
     * Simulates with the given protocol.
     *
     * @param protocol protocol to simulate with.
     * @return state after simulation.
     */
    private State simulateWith(Protocol protocol) {
        State state = State.create(network, destinationId, protocol);

        engine.simulate(state, destinationId);

        return state;
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node0GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node1GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node2GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node3GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node4GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node5GetsExpectedTable() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node0SelectsRouteWith0AndEmptyPath() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(0)).getSelectedRoute(), is(route(destinationId, sp(0), path())));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node1SelectsRouteWith0AndPathWithNode0() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(1)).getSelectedRoute(), is(route(destinationId, sp(0), path(0))));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node2SelectsRouteWith5AndPathWithNode0() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(2)).getSelectedRoute(), is(route(destinationId, sp(5), path(0))));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Node3SelectsRouteWith1AndPathWithNodes1And0() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(3)).getSelectedRoute(), is(route(destinationId, sp(1), path(1, 0))));
    }

    @Test(timeout = 2000)
    public void
    simulate_BGPProtocolAndFIFOScheduler_Node4SelectsRouteWith3AndPathWithNodes5And3And1And0() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(4)).getSelectedRoute(), is(route(destinationId, sp(3), path(5, 3, 1, 0))));
    }

    @Test(timeout = 2000)
    public void
    simulate_BGPProtocolAndFIFOScheduler_Node5SelectsRouteWith2AndPathWithNodesAnd3And1And0() throws Exception {
        State state = simulateWith(new BGPProtocol());

        assertThat(state.get(new Node(5)).getSelectedRoute(), is(route(destinationId, sp(2), path(3, 1, 0))));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_ConvergesToSameRouteTablesAsWithBGPProtocol() throws Exception {
        State state = State.create(network, destinationId, new D1R1Protocol());

        engine.simulate(state, destinationId);

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
        eventHandler.register(engine.getEventGenerator());
        State state = State.create(network, destinationId, new D1R1Protocol());

        engine.simulate(state, destinationId);

        assertThat(eventHandler.getDetectionCount(), is(0));
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
        // TODO Link Breaker
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
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
        State state = State.create(network, destinationId, new D2R1Protocol());

        engine.simulate(state, destinationId);

        assertThat(state.get(new Node(0)).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(new Node(1)).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(new Node(2)).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(new Node(3)).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(new Node(4)).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(new Node(5)).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
        eventHandler.register(engine.getEventGenerator());
        State state = State.create(network, destinationId, new D2R1Protocol());

        engine.simulate(state, destinationId);

        assertThat(eventHandler.getDetectionCount(), is(0));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant3_Converges() throws Exception {
        // TODO Link Breaker
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
        // TODO Link Breaker
//        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
//                .linkBreaker(new FixedLinkBreaker(new Link(3, 1, splabel(1)), 2L))
//                .eventHandler(eventHandler)
//                .build();
//
//        engine.simulate(network, new D2R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(0));
    }
}
