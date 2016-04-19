package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathAttribute;
import policies.implementations.shortestpath.ShortestPathLabel;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.shortestpath.Topology1;
import simulation.networks.shortestpath.Topology2;
import simulation.networks.shortestpath.Topology3;
import simulation.networks.shortestpath.Topology4;
import wrappers.routetable.OutLinkElement;
import wrappers.routetable.RouteElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.outLink;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalid;
import static wrappers.routetable.RouteElement.route;
import static wrappers.routetable.RouteTableWrapper.table;

public class SimulateEngineBGPAndShortestPathTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new ShortestPathPolicy(),
                new FIFOScheduler(), new DebugEventHandler());
    }

    /**
     * Wrapper around the ShortestPathLabel constructor to improve readability.
     *
     * @param length length of the label.
     * @return a new ShortestPathLabel instance assign the given length.
     */
    private static ShortestPathLabel label(int length) {
        return new ShortestPathLabel(length);
    }

    /**
     * Wrapper around the usual link wrapper for shortest path labels.
     *
     * @return a new link instance with a label with the given length.
     */
    private static OutLinkElement splink(int srcId, int destId, int length) {
        return outLink(srcId, destId, new ShortestPathLabel(length));
    }

    /**
     * Wrapper around the usual route wrapper for shortest path attributes.
     *
     * @return a new route instance with a attribute and path.
     */
    private static RouteElement sproute(int length, PathAttribute path) {
        return route(new ShortestPathAttribute(length), path);
    }

    @Test(timeout = 2000)
    public void simulate_Topology0_Converges() throws Exception {
        Network network0 = network(
                link(from(0), to(1), label(1))
        );

        engine.simulate(network0, 1);

        assertThat(engine.getRouteTable(new Node(0)), is( table(
                                selfLink(0), splink(0, 1, 1),
                destination(1), invalid(),   sproute(1, path(1))
        )));

        assertThat(engine.getRouteTable(new Node(1)), is( table(
                                selfLink(1),
                destination(1), sproute(0, path())
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Topology1_Converges() throws Exception {
        topology = new Topology1();
        engine.simulate(topology.getNetwork());
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP()));
    }

    @Test(timeout = 2000)
    public void simulate_Topology2_Converges() throws Exception {
        topology = new Topology2();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP(0)));
    }

    @Test(timeout = 2000)
    @Ignore
    public void simulate_Topology3_DoesNotConverge() throws Exception {
        topology = new Topology3();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP(0)));
    }

    @Test(timeout = 2000)
    public void simulate_Topology4_Converges() throws Exception {
        topology = new Topology4();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP(0)));
    }
}