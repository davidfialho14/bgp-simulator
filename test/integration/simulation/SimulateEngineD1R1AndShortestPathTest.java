package simulation;

import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathAttributeFactory;
import protocols.implementations.D1R1Protocol;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.shortestpath.Topology3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimulateEngineD1R1AndShortestPathTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new SimulateEngine(new D1R1Protocol(), new ShortestPathAttributeFactory(),
                new FIFOScheduler(), new DebugEventHandler());
    }

    @Test(timeout = 2000)
    public void simulate_Topology3_Converges() throws Exception {
        topology = new Topology3();
        engine.simulate(topology.getNetwork(), 0);
        printTables();

        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForD1R1(0)));
    }
}