package networks;

import addons.eventhandlers.MessageAndDetectionCountHandler;
import addons.linkbreakers.FixedTimeLinkBreaker;
import addons.linkbreakers.LinkBreaker;
import factories.ShortestPathNetworkFactory;
import network.Link;
import network.Network;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
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
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

public class ShortestPathNetwork6Test extends ShortestPathNetworkTest {

    private Engine engine;
    private Network network;
    private int destinationId = 0;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        network = new ShortestPathNetworkFactory().network(6);
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

    // ----- BGP PROTOCOL -----

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_GetsExpectedTables() throws Exception {
        State state = simulateWith(new BGPProtocol());

        collector.checkThat(state.get(0).getTable(), is(BGPProtocolExpectedTables[0]));
        collector.checkThat(state.get(1).getTable(), is(BGPProtocolExpectedTables[1]));
        collector.checkThat(state.get(2).getTable(), is(BGPProtocolExpectedTables[2]));
        collector.checkThat(state.get(3).getTable(), is(BGPProtocolExpectedTables[3]));
        collector.checkThat(state.get(4).getTable(), is(BGPProtocolExpectedTables[4]));
        collector.checkThat(state.get(5).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_CorrectSelectedRoutes() throws Exception {
        State state = simulateWith(new BGPProtocol());

        collector.checkThat(state.get(0).getSelectedRoute(), is(route(destinationId, sp(0), path())));
        collector.checkThat(state.get(1).getSelectedRoute(), is(route(destinationId, sp(0), path(0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(destinationId, sp(5), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(destinationId, sp(1), path(1, 0))));
        collector.checkThat(state.get(4).getSelectedRoute(), is(route(destinationId, sp(3), path(5, 3, 1, 0))));
        collector.checkThat(state.get(5).getSelectedRoute(), is(route(destinationId, sp(2), path(3, 1, 0))));
    }

    // ----- D1R1 PROTOCOL -----

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_ConvergesToSameRouteTablesAsWithBGPProtocol() throws Exception {
        State state = simulateWith(new D1R1Protocol());

        assertThat(state.get(0).getTable(), is(BGPProtocolExpectedTables[0]));
        assertThat(state.get(1).getTable(), is(BGPProtocolExpectedTables[1]));
        assertThat(state.get(2).getTable(), is(BGPProtocolExpectedTables[2]));
        assertThat(state.get(3).getTable(), is(BGPProtocolExpectedTables[3]));
        assertThat(state.get(4).getTable(), is(BGPProtocolExpectedTables[4]));
        assertThat(state.get(5).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_CorrectSelectedRoutes() throws Exception {
        State state = simulateWith(new D1R1Protocol());

        collector.checkThat(state.get(0).getSelectedRoute(), is(route(destinationId, sp(0), path())));
        collector.checkThat(state.get(1).getSelectedRoute(), is(route(destinationId, sp(0), path(0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(destinationId, sp(5), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(destinationId, sp(1), path(1, 0))));
        collector.checkThat(state.get(4).getSelectedRoute(), is(route(destinationId, sp(3), path(5, 3, 1, 0))));
        collector.checkThat(state.get(5).getSelectedRoute(), is(route(destinationId, sp(2), path(3, 1, 0))));
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
        eventHandler.register(engine.getEventGenerator());

        simulateWith(new D1R1Protocol());

        assertThat(eventHandler.getDetectionCount(), is(0));
    }

    // ----- D2R1 PROTOCOL -----

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_ConvergesToSameRouteTablesAsWithBGPProtocol() throws Exception {
        State state = simulateWith(new D2R1Protocol());

        collector.checkThat(state.get(0).getTable(), is(BGPProtocolExpectedTables[0]));
        collector.checkThat(state.get(1).getTable(), is(BGPProtocolExpectedTables[1]));
        collector.checkThat(state.get(2).getTable(), is(BGPProtocolExpectedTables[2]));
        collector.checkThat(state.get(3).getTable(), is(BGPProtocolExpectedTables[3]));
        collector.checkThat(state.get(4).getTable(), is(BGPProtocolExpectedTables[4]));
        collector.checkThat(state.get(5).getTable(), is(BGPProtocolExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_CorrectSelectedRoutes() throws Exception {
        State state = simulateWith(new D2R1Protocol());

        collector.checkThat(state.get(0).getSelectedRoute(), is(route(destinationId, sp(0), path())));
        collector.checkThat(state.get(1).getSelectedRoute(), is(route(destinationId, sp(0), path(0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(destinationId, sp(5), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(destinationId, sp(1), path(1, 0))));
        collector.checkThat(state.get(4).getSelectedRoute(), is(route(destinationId, sp(3), path(5, 3, 1, 0))));
        collector.checkThat(state.get(5).getSelectedRoute(), is(route(destinationId, sp(2), path(3, 1, 0))));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOScheduler_NeverDetects() throws Exception {
        MessageAndDetectionCountHandler eventHandler = new MessageAndDetectionCountHandler();
        eventHandler.register(engine.getEventGenerator());
        State state = State.create(network, destinationId, new D2R1Protocol());

        engine.simulate(state, destinationId);

        assertThat(eventHandler.getDetectionCount(), is(0));
    }

    // ----- LINK BREAKERS -----

    private State simulateBreak(Protocol protocol, Link linkToBreak, long timeToBreak) {
        State state = State.create(network, destinationId, protocol);

        LinkBreaker linkBreaker = new FixedTimeLinkBreaker(linkToBreak, timeToBreak);
        linkBreaker.assignTo(engine, state);

        engine.simulate(state, destinationId);

        return state;
    }

    @Test(timeout = 2000)
    public void simulate_D1R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant1_Converges() throws Exception {
        State state = simulateBreak(new D1R1Protocol(), new Link(3, 1, splabel(1)), 1L);

        collector.checkThat(state.get(0).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[0]));
        collector.checkThat(state.get(1).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[1]));
        collector.checkThat(state.get(2).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[2]));
        collector.checkThat(state.get(3).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[3]));
        collector.checkThat(state.get(4).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[4]));
        collector.checkThat(state.get(5).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[5]));
    }

    @Test(timeout = 2000)
    public void simulate_D2R1ProtocolAndFIFOSchedulerAndBreakingLink3To1OnInstant1_Converges() throws Exception {
        State state = simulateBreak(new D2R1Protocol(), new Link(3, 1, splabel(1)), 1L);

        collector.checkThat(state.get(0).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[0]));
        collector.checkThat(state.get(1).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[1]));
        collector.checkThat(state.get(2).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[2]));
        collector.checkThat(state.get(3).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[3]));
        collector.checkThat(state.get(4).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[4]));
        collector.checkThat(state.get(5).getTable(), is(BGPProtocolAndBrokenLink3To1ExpectedTables[5]));
    }
}
