package core.implementations.handlers;

import addons.eventhandlers.MessageAndDetectionCountHandler;
import core.Engine;
import core.State;
import core.schedulers.FIFOScheduler;
import core.topology.Topology;
import org.junit.Before;
import org.junit.Test;
import policies.shortestpath.ShortestPathPolicy;
import protocols.BGPProtocol;
import protocols.D1R1Protocol;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wrappers.ShortestPathWrapper.label;
import static wrappers.topology.FromNodeElement.from;
import static wrappers.topology.LinkElement.link;
import static wrappers.topology.ToNodeElement.to;
import static wrappers.topology.TopologyWrapper.topology;

public class MessageAndDetectionCountHandlerTest {

    private static Topology topology3 = topology(new ShortestPathPolicy(),
            link(from(1), to(0), label(0)),
            link(from(2), to(0), label(0)),
            link(from(3), to(0), label(0)),
            link(from(1), to(2), label(-1)),
            link(from(2), to(3), label(1)),
            link(from(3), to(1), label(-2)));
    private MessageAndDetectionCountHandler eventHandler;
    private Engine engine;

    @Before
    public void setUp() throws Exception {
        eventHandler = new MessageAndDetectionCountHandler();
        engine = new Engine(new FIFOScheduler());
        eventHandler.register(engine.getEventGenerator());
    }

    @Test
    public void messageCount_ForTopology0AndDestination1WithBGP_Is1() throws Exception {
        int destinationId = 1;
        State state = State.create(
                topology(new ShortestPathPolicy(),
                        link(from(0), to(1), label(1))),
                destinationId,
                new BGPProtocol());

        engine.simulate(state);

        assertThat(eventHandler.getMessageCount(), is(1));
    }

    @Test
    public void messageCount_ForTopology1AndDestination2_Is3() throws Exception {
        int destinationId = 2;
        State state = State.create(
                topology(new ShortestPathPolicy(),
                        link(from(0), to(1), label(1)),
                        link(from(1), to(2), label(1)),
                        link(from(0), to(2), label(0))),
                destinationId,
                new BGPProtocol());

        engine.simulate(state);

        assertThat(eventHandler.getMessageCount(), is(3));
    }

    @Test
    public void messageCount_ForTopology3WithD1R1_Is13() throws Exception {
        int destinationId = 0;
        State state = State.create(topology3, destinationId, new D1R1Protocol());

        engine.simulate(state);

        assertThat(eventHandler.getMessageCount(), is(13));
    }

    @Test
    public void detectionCount_ForTopology3WithD1R1_Is2() throws Exception {
        int destinationId = 0;
        State state = State.create(topology3, destinationId, new D1R1Protocol());

        engine.simulate(state);

        assertThat(eventHandler.getDetectionCount(), is(2));
    }
}