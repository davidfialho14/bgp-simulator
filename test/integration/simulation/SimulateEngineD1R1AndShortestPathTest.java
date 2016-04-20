package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.D1R1Protocol;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.ShortestPathWrapper.label;
import static wrappers.ShortestPathWrapper.splink;
import static wrappers.ShortestPathWrapper.sproute;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalid;
import static wrappers.routetable.RouteTableWrapper.table;

public class SimulateEngineD1R1AndShortestPathTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new D1R1Protocol(), new ShortestPathPolicy(),
                new FIFOScheduler(), new DebugEventHandler());
    }

    @Test(timeout = 2000)
    public void simulate_Topology3_Converges() throws Exception {
        Network network3 = network(
                link(from(1), to(0), label(0)),
                link(from(2), to(0), label(0)),
                link(from(3), to(0), label(0)),
                link(from(1), to(2), label(-1)),
                link(from(2), to(3), label(1)),
                link(from(3), to(1), label(-2))
        );

        engine.simulate(network3, 0);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1), splink(1, 0, 0),     splink(1, 2, -1),
                destination(0), invalid(),   sproute(0, path(0)), invalid()
        )));

        assertThat(engine.getRouteTable(new Node(2)), is( table(
                                selfLink(2), splink(2, 0, 0),     splink(2, 3, 1),
                destination(0), invalid(),   sproute(0, path(0)), invalid()
        )));

        assertThat(engine.getRouteTable(new Node(3)), is( table(
                                selfLink(3), splink(3, 0, 0),     splink(3, 1, -2),
                destination(0), invalid(),   sproute(0, path(0)), sproute(-2, path(1, 0))
        )));
    }
}