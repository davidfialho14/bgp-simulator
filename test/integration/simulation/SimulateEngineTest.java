package simulation;

import network.Node;

import java.util.Map;

public class SimulateEngineTest {

    SimulateEngine engine;

    protected void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }
}