package simulation.implementations.handlers;

import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import simulation.SimulateEngine;
import simulation.implementations.schedulers.FIFOScheduler;
import simulation.networks.Topology;
import simulation.networks.shortestpath.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MessageAndDetectionCountHandlerTest {

    private MessageAndDetectionCountHandler eventHandler;
    private SimulateEngine engine;

    @Before
    public void setUp() throws Exception {
        eventHandler = new MessageAndDetectionCountHandler();
        engine = new SimulateEngine(new BGPProtocol(), new ShortestPathPolicy(), new FIFOScheduler(), eventHandler);
    }

    @Test
    public void Topology0_MessageCountIs1() throws Exception {
        Topology topology = new Topology0();
        engine.simulate(topology.getNetwork());

        assertThat(eventHandler.getMessageCount(), is(1));
    }

    @Test
    public void Topology1_MessageCountIs1() throws Exception {
        Topology topology = new Topology1();
        engine.simulate(topology.getNetwork());

        assertThat(eventHandler.getMessageCount(), is(4));
    }
}