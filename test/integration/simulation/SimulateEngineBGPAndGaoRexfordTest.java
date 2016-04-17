package simulation;
//
//import org.junit.Before;
//import org.junit.Test;
//import policies.implementations.gaorexford.GaoRexfordPolicy;
//import protocols.implementations.BGPProtocol;
//import simulation.implementations.handlers.DebugEventHandler;
//import simulation.implementations.schedulers.FIFOScheduler;
//import simulation.networks.gaorexford.Topology0;
//import simulation.networks.gaorexford.Topology1;
//import simulation.networks.gaorexford.Topology2;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
//public class SimulateEngineBGPAndGaoRexfordTest extends SimulateEngineTest {
//
//    @Before
//    public void setUp() throws Exception {
//        engine = new SimulateEngine(new BGPProtocol(), new GaoRexfordPolicy(),
//                new FIFOScheduler(), new DebugEventHandler());
//    }
//
//    @Test(timeout = 2000)
//    public void simulate_Topology0_Converges() throws Exception {
//        topology = new Topology0();
//        engine.simulate(topology.getNetwork());
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP()));
//    }
//
//    @Test(timeout = 2000)
//    public void simulate_Topology1_Converges() throws Exception {
//        topology = new Topology1();
//        engine.simulate(topology.getNetwork());
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP()));
//    }
//
//    @Test(timeout = 2000)
//    public void simulate_Topology2_Converges() throws Exception {
//        topology = new Topology2();
//        engine.simulate(topology.getNetwork());
//        printTables();
//
//        assertThat(engine.getRouteTables(), is(topology.getExpectedRouteTablesForBGP()));
//    }
//}