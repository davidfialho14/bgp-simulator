package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import policies.implementations.gaorexford.GaoRexfordPolicy;
import protocols.implementations.BGPProtocol;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.gaorexford.Topology2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.GaoRexfordWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalid;
import static wrappers.routetable.RouteTableWrapper.table;

public class SimulateEngineBGPAndGaoRexfordTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new GaoRexfordPolicy(),
                new FIFOScheduler(), new DebugEventHandler());
    }

    @Test(timeout = 2000)
    public void simulate_Topology0_Converges() throws Exception {
        Network network0 = network(
                link(from(0), to(1), customerLabel()),
                link(from(1), to(0), providerLabel())
        );

        engine.simulate(network0);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0),       customerLink(0, 1),
                destination(0), selfRoute(), invalid(),
                destination(1), invalid(),         customerRoute(path(1))
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),       providerLink(1, 0),
                destination(0), invalid(),         providerRoute(path(0)),
                destination(1), selfRoute(), invalid()
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology1_Converges() throws Exception {
        Network network1 = network(
                link(from(0), to(1), customerLabel()),
                link(from(1), to(0), providerLabel()),
                link(from(2), to(1), customerLabel()),
                link(from(1), to(2), providerLabel())
        );

        engine.simulate(network1);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0), customerLink(0, 1),
                destination(0), selfRoute(), invalid(),
                destination(1), invalid(),   customerRoute(path(1)),
                destination(2), invalid(),   invalid()
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1), providerLink(1, 0),     providerLink(1, 2),
                destination(0), invalid(),   providerRoute(path(0)), invalid(),
                destination(1), selfRoute(), invalid(),              invalid(),
                destination(2), invalid(),   invalid(),              providerRoute(path(2))
        )));

        assertThat(engine.getRouteTable(new Node(2)), is( table(
                                selfLink(2), customerLink(2, 1),
                destination(0), invalid(),   invalid(),
                destination(1), invalid(),   customerRoute(path(1)),
                destination(2), selfRoute(), invalid()
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology2_Converges() throws Exception {
        topology = new Topology2();
        engine.simulate(topology.getNetwork());
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP()));
    }
}