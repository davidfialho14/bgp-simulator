package simulation.implementations.handlers;

import network.Network;
import org.junit.Before;
import org.junit.Test;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.Protocol;
import protocols.implementations.BGPProtocol;
import protocols.implementations.D1R1Protocol;
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
    }

    private SimulateEngine engineWithProtocol(Protocol protocol) {
        return new SimulateEngine(protocol, new ShortestPathPolicy(), new FIFOScheduler(), eventHandler);
    }

    private static BGPProtocol BGP() {
        return new BGPProtocol();
    }

    private static D1R1Protocol D1R1() {
        return new D1R1Protocol();
    }

    @Test
    public void messageCount_ForTopology0WithBGP_Is1() throws Exception {
        engine = engineWithProtocol(BGP());
        Network network0 = network(
                link(from(0), to(1), label(1))
        );

        engine.simulate(network0);

        assertThat(eventHandler.getMessageCount(), is(1));
    }

    @Test
    public void messageCount_ForTopology1_Is4() throws Exception {
        engine = engineWithProtocol(BGP());
        Network network1 = network(
                link(from(0), to(1), label(1)),
                link(from(1), to(2), label(1)),
                link(from(0), to(2), label(0))
        );

        engine.simulate(network1);

        assertThat(eventHandler.getMessageCount(), is(4));
    }

    private static Network network3 = network(
            link(from(1), to(0), label(0)),
            link(from(2), to(0), label(0)),
            link(from(3), to(0), label(0)),
            link(from(1), to(2), label(-1)),
            link(from(2), to(3), label(1)),
            link(from(3), to(1), label(-2))
    );

    @Test
    public void messageCount_ForTopology3WithD1R1_Is() throws Exception {
        engine = engineWithProtocol(D1R1());

        engine.simulate(network3, 0);

        assertThat(eventHandler.getMessageCount(), is(13));
    }

    @Test
    public void detectionCount_ForTopology3WithD1R1_Is2() throws Exception {
        engine = engineWithProtocol(D1R1());

        engine.simulate(network3, 0);

        assertThat(eventHandler.getDetectionCount(), is(2));
    }
}