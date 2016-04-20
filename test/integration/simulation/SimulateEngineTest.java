package simulation;

import network.Node;
import simulation.implementations.handlers.NullEventHandler;

import java.util.Map;

public class SimulateEngineTest {

    protected SimulateEngine engine;
    protected EventHandler eventHandler = new NullEventHandler();

    protected void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }
}