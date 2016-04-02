package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;
import simulation.implementations.protocols.BGPProtocol;
import simulation.implementations.schedulers.FIFOScheduler;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SimulateEngineTest {

    SimulateEngine engine;

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new ShortestPathAttributeFactory(),
                new FIFOScheduler(), null);
    }

    private void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }

    @Test(timeout = 5000)
    public void simulate_Network0_Converges() throws Exception {
        Network network0 = NetworkCreator.createNetwork0();
        engine.simulate(network0);
        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork0(network0)));
        printTables();
    }

    @Test(timeout = 5000)
    public void simulate_Network1_Converges() throws Exception {
        Network network1 = NetworkCreator.createNetwork1();
        engine.simulate(network1);
        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork1(network1)));
        printTables();
    }

    @Test
    public void simulate_Network2_Converges() throws Exception {
        Network network = NetworkCreator.createNetwork2();
        engine.simulate(network, 5);
        printTables();
    }

    @Test
    public void simulate_Network3_DoesNotConverge() throws Exception {
        Network network = NetworkCreator.createNetwork3();
        engine.simulate(network, 0);
        printTables();
    }
}