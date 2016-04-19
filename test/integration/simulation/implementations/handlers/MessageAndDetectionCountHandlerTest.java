package simulation.implementations.handlers;

import network.Network;
import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import simulation.SimulateEngine;
import simulation.implementations.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.ShortestPathWrapper.label;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;

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
        Network network0 = network(
                link(from(0), to(1), label(1))
        );

        engine.simulate(network0);

        assertThat(eventHandler.getMessageCount(), is(1));
    }

    @Test
    public void Topology1_MessageCountIs1() throws Exception {
        Network network1 = network(
                link(from(0), to(1), label(1)),
                link(from(1), to(2), label(1)),
                link(from(0), to(2), label(0))
        );

        engine.simulate(network1);

        assertThat(eventHandler.getMessageCount(), is(4));
    }
}