import io.TopologyParser;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import simulation.RouteTable;
import simulation.SimulateEngine;
import simulation.implementations.handlers.MessageAndDetectionCountHandler;
import simulation.implementations.schedulers.FIFOScheduler;

import java.util.Map;

public class AllTest {

    private TopologyParser parser;
    private SimulateEngine engine;
    private MessageAndDetectionCountHandler eventHandler;

    @Before
    public void setUp() throws Exception {
        parser = new TopologyParser();
        eventHandler = new MessageAndDetectionCountHandler();
    }

    protected void printTables() {
        for (Map.Entry<Node, RouteTable> entry : engine.getRouteTables().entrySet()) {
            System.out.println(entry.getKey()); // print the node
            entry.getValue().getPrintableTable().printTable();
            System.out.println();
        }
    }

    @Test
    public void example0() throws Exception {
        parser.parse("test/example0.gv");
        engine = new SimulateEngine(parser.getProtocol(), parser.getPolicy().getAttributeFactory(),
                new FIFOScheduler(), eventHandler);

        engine.simulate(parser.getParsedNetwork());

        printTables();

        System.out.println("Exchanged Message Count: " + eventHandler.getMessageCount());
        System.out.println("Detection Count: " + eventHandler.getDetectionCount());
    }

    @Test
    public void example1() throws Exception {
        parser.parse("test/example1.gv");
        engine = new SimulateEngine(parser.getProtocol(), parser.getPolicy().getAttributeFactory(),
                new FIFOScheduler(), eventHandler);

        engine.simulate(parser.getParsedNetwork());

        printTables();

        System.out.println("Exchanged Message Count: " + eventHandler.getMessageCount());
        System.out.println("Detection Count: " + eventHandler.getDetectionCount());
    }
}
