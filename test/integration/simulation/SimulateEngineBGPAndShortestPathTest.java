package simulation;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.policies.shortestpath.ShortestPathAttributeFactory;
import simulation.implementations.protocols.BGPProtocol;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.shortestpath.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimulateEngineBGPAndShortestPathTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new BGPProtocol(), new ShortestPathAttributeFactory(),
                new FIFOScheduler(), new DebugEventHandler());
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
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables(0)));
    }

    @Test(timeout = 2000)
    @Ignore
    public void simulate_Topology3_DoesNotConverge() throws Exception {
        topology = new Topology3();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables(0)));
    }

    @Test//(timeout = 2000)
    public void simulate_Topology4_Converges() throws Exception {
        topology = new Topology4();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTables(0)));
    }
}