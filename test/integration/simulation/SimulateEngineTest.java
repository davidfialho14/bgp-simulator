package simulation;

import network.Node;
import simulation.networks.Topology;

import java.util.Map;

public class SimulateEngineTest {

    SimulateEngine engine;
    Topology topology;

    protected void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }
}