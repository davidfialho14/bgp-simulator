package addons.protocolchangers;

import addons.eventhandlers.MessageAndDetectionCountHandler;
import factories.NetworkFactory;
import factories.ShortestPathNetworkFactory;
import network.Node;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import protocols.BGPProtocol;
import protocols.D1R1Protocol;
import protocols.Protocol;
import simulation.Engine;
import simulation.State;
import simulation.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.ShortestPathWrapper.sp;

public class NodeSetFixedTimeProtocolChangerTest {

    private NetworkFactory factory = new ShortestPathNetworkFactory();
    private Engine engine;

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
    }

    /**
     *
     * @param networkId ID of the network to simulate.
     * @param destId ID of the destination.
     * @param protocol protocol to change to.
     * @param nodeId ID of the node to change the protocol.
     * @param time time to change the protocol.
     * @return final state.
     */
    private State simulate(int networkId, int destId, Protocol protocol, int nodeId, long time) {
        State state = State.create(factory.network(networkId), destId, new BGPProtocol());
        new NodeSetFixedTimeProtocolChanger(engine, state, time, protocol, new Node(nodeId));

        engine.simulate(state, 0);

        return state;
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant3WhileSimulatingNetwork3ForDestination0_CorrectSelectedRoutes()
            throws Exception {
        State state = simulate(3, 0, new D1R1Protocol(), 2, 3L);

        collector.checkThat(state.get(1).getSelectedRoute(), is(route(0, sp(-1), path(2, 0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(0, sp(0), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(0, sp(-3), path(1, 2, 0))));
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant3WhileSimulatingNetwork3ForDestination0_MessageCountIs14()
            throws Exception {
        MessageAndDetectionCountHandler handler = new MessageAndDetectionCountHandler();
        handler.register(engine.getEventGenerator());

        simulate(3, 0, new D1R1Protocol(), 2, 3L);

        assertThat(handler.getMessageCount(), is(15));
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant3WhileSimulatingNetwork3ForDestination0_DetectionCountIs1()
            throws Exception {
        MessageAndDetectionCountHandler handler = new MessageAndDetectionCountHandler();
        handler.register(engine.getEventGenerator());

        simulate(3, 0, new D1R1Protocol(), 2, 3L);

        assertThat(handler.getDetectionCount(), is(1));
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant15WhileSimulatingNetwork3ForDestination0_CorrectSelectedRoutes()
            throws Exception {
        State state = simulate(3, 0, new D1R1Protocol(), 2, 15L);

        collector.checkThat(state.get(1).getSelectedRoute(), is(route(0, sp(-1), path(2, 0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(0, sp(0), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(0, sp(-3), path(1, 2, 0))));
    }

}