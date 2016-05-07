package simulation;

import network.Link;
import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathLabel;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import simulation.implementations.linkbreakers.FixedLinkBreaker;
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

/*
    Allow duplicates in order to make the tests easier to understand without having to look for the network or
    the expected tables elsewhere.
 */
@SuppressWarnings("Duplicates")
public class EngineBGPAndShortestPathTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new Engine.Builder(
                new ShortestPathPolicy(),
                new FIFOScheduler())
                .eventHandler(eventHandler)
                .build();

        protocol = new BGPProtocol();
    }

    @Test(timeout = 2000)
    public void simulate_Topology0_Converges() throws Exception {
        Network network0 = network(
                link(from(0), to(1), label(1))
        );

        engine.simulate(network0, protocol, 1);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),    splink(0, 1, 1),
                destination(1), invalidRoute(), sproute(1, path(1))
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),
                destination(1), sproute(0, path())
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology1_Converges() throws Exception {
        Network network1 = network(
                link(from(0), to(1), label(1)),
                link(from(1), to(2), label(1)),
                link(from(0), to(2), label(0))
        );

        engine.simulate(network1, protocol);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),        splink(0, 1, 1),        splink(0, 2, 0),
                destination(0), sproute(0, path()), invalidRoute(),         invalidRoute(),
                destination(1), invalidRoute(),     sproute(1, path(1)),    invalidRoute(),
                destination(2), invalidRoute(),     sproute(2, path(1, 2)), sproute(0, path(2))
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),        splink(1, 2, 1),
                destination(1), sproute(0, path()), invalidRoute(),
                destination(2), invalidRoute(),     sproute(1, path(2))
        )));

        assertThat(engine.getRouteTable(new Node(2)), is( table(
                                selfLink(2),
                destination(2), sproute(0, path())
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology2_Converges() throws Exception {
        Network network2 = network(
                link(from(0), to(1), label(1)),
                link(from(1), to(2), label(1)),
                link(from(2), to(0), label(1))
        );

        engine.simulate(network2, protocol, 0);

        assertThat(engine.getRouteTable(new Node(0)), is(table(
                                selfLink(0),        splink(0, 1, 1),
                destination(0), sproute(0, path()), invalidRoute()
        )));

        assertThat(engine.getRouteTable(new Node(1)), is(table(
                                selfLink(1),    splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(2, path(2, 0))
        )));

        assertThat(engine.getRouteTable(new Node(2)), is(table(
                                selfLink(2),    splink(2, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));
    }

    @Test(timeout = 2000)
    @Ignore
    public void simulate_Topology3_DoesNotConverge() throws Exception {
        Network network3 = network(
                link(from(1), to(0), label(0)),
                link(from(2), to(0), label(0)),
                link(from(3), to(0), label(0)),
                link(from(1), to(2), label(-1)),
                link(from(2), to(3), label(1)),
                link(from(3), to(1), label(-2))
        );

        engine.simulate(network3, protocol, 0);
    }

    @Test(timeout = 2000)
    public void simulate_Topology4_Converges() throws Exception {
        Network network4 = network(
                link(from(1), to(0), label(0)),
                link(from(1), to(2), label(1)),
                link(from(2), to(3), label(1)),
                link(from(3), to(1), label(1))
        );

        engine.simulate(network4, protocol, 0);

        assertThat(engine.getRouteTable(new Node(0)), is(table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(engine.getRouteTable(new Node(1)), is(table(
                                selfLink(1),    splink(1, 0, 0),     splink(1, 2, 1),
                destination(0), invalidRoute(), sproute(0, path(0)), invalidRoute()
        )));

        assertThat(engine.getRouteTable(new Node(2)), is(table(
                                selfLink(2),    splink(2, 3, 1),
                destination(0), invalidRoute(), sproute(2, path(3, 1, 0))
        )));

        assertThat(engine.getRouteTable(new Node(3)), is(table(
                                selfLink(3),    splink(3, 1, 1),
                destination(0), invalidRoute(), sproute(1, path(1, 0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology5_Converges() throws Exception {
        Network network0 = network(
                link(from(2), to(1), label(1)),
                link(from(1), to(0), label(1))
        );

        engine.simulate(network0, protocol, 0);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));

        assertThat(engine.getRouteTable(new Node(2)), is( table(
                                selfLink(2),    splink(2, 1, 1),
                destination(0), invalidRoute(), sproute(2, path(1, 0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology5BreakingLink2To1OnInstant1_Converges() throws Exception {
        engine.setLinkBreaker(new FixedLinkBreaker(new Link(2, 1, new ShortestPathLabel(1)), 1L));

        Network network0 = network(
                link(from(2), to(1), label(1)),
                link(from(1), to(0), label(1))
        );

        engine.simulate(network0, protocol, 0);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));

        assertThat(engine.getRouteTable(new Node(2)), is( table(
                                selfLink(2),
                destination(0), invalidRoute()
        )));
    }
}