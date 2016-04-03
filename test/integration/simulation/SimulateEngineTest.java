package simulation;

import network.Node;
import org.junit.Before;
import org.junit.Test;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;
import simulation.implementations.protocols.BGPProtocol;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.Topology;
import simulation.networks.shortestpath.Topology0;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimulateEngineTest {

    SimulateEngine engine;
    Topology topology;

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new ShortestPathAttributeFactory(),
                new FIFOScheduler(), new DebugEventHandler());
    }

    private void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }

    @Test(timeout = 2000)
    public void simulate_Topology0_Converges() throws Exception {
        topology = new Topology0();
        engine.simulate(topology.getNetwork());
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables()));
    }
//
//    @Test(timeout = 2000)
//    public void simulate_Network1_Converges() throws Exception {
//        Network network1 = NetworkCreator.createNetwork1();
//        engine.simulate(network1);
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork1(network1)));
//    }
//
//    @Test(timeout = 2000)
//    public void simulate_Network2_Converges() throws Exception {
//        Network network2 = NetworkCreator.createNetwork2();
//        engine.simulate(network2, 0);
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork2ForDestination0(network2)));
//    }
//
//    @Test//(timeout = 2000)
//    public void simulate_Network4_Converges() throws Exception {
//        Network network4 = NetworkCreator.createNetwork4();
//        engine.simulate(network4, 0);
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork4ForDestination0(network4)));
//    }
}