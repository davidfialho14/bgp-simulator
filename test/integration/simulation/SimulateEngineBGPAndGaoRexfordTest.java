package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import simulation.implementations.handlers.DebugEventHandler;
import simulation.implementations.policies.gaorexford.CustomerLabel;
import simulation.implementations.policies.gaorexford.GaoRexfordAttributeFactory;
import simulation.implementations.policies.gaorexford.ProviderLabel;
import simulation.implementations.protocols.BGPProtocol;
import simulation.implementations.schedulers.FIFOScheduler;

import java.util.Map;

public class SimulateEngineBGPAndGaoRexfordTest {

    SimulateEngine engine;

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
    public void simulate_Network0_Converges() throws Exception {
        Network network0 = new Network();
        network0.addNode(0);
        network0.addNode(1);
        network0.link(0, 1, new CustomerLabel());
        network0.link(1, 0, new ProviderLabel());

        engine.simulate(network0);
        printTables();

//        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork0(network0)));
    }

    @Test(timeout = 2000)
    public void simulate_Network1_Converges() throws Exception {
        Network network1 = new Network();
        network1.addNode(0);
        network1.addNode(1);
        network1.addNode(2);
        network1.link(0, 1, new CustomerLabel());
        network1.link(1, 0, new ProviderLabel());
        network1.link(2, 1, new CustomerLabel());
        network1.link(1, 2, new ProviderLabel());

        engine.simulate(network1);
        printTables();

//        assertThat(engine.getRouteTables(), is(NetworkCreator.expectedRouteTableForNetwork0(network0)));
    }
}