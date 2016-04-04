package simulation;

import network.Node;
import org.junit.Before;
import org.junit.Test;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.policies.gaorexford.GaoRexfordAttributeFactory;
import simulation.implementations.protocols.BGPProtocol;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.Topology;
import simulation.networks.gaorexford.*;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimulateEngineBGPAndGaoRexfordTest {

    SimulateEngine engine;
    Topology topology;

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new GaoRexfordAttributeFactory(),
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

    @Test(timeout = 2000)
    public void simulate_Topology1_Converges() throws Exception {
        topology = new Topology1();
        engine.simulate(topology.getNetwork());
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables()));
    }

    @Test(timeout = 2000)
    public void simulate_Topology2_Converges() throws Exception {
        topology = new Topology2();
        engine.simulate(topology.getNetwork());
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables()));
    }
}