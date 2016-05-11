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
import simulation.Engine;
import simulation.State;
import simulation.schedulers.FIFOScheduler;

import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.RouteWrapper.route;
import static wrappers.ShortestPathWrapper.sp;

public class NodeSetFixedTimeProtocolChangerTest {

    private NetworkFactory factory = new ShortestPathNetworkFactory();
    private Engine engine;

    @Rule
    public ErrorCollector collector;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        collector = new ErrorCollector();
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant3WhileSimulatingNetwork3ForDestination0_CorrectSelectedRoutes()
            throws Exception {
        State state = State.create(factory.network(3), 0, new BGPProtocol());   // start all with the BGP protocol
        new NodeSetFixedTimeProtocolChanger(engine, state, 3L, new D1R1Protocol(), new Node(2));

        engine.simulate(state, 0);

        collector.checkThat(state.get(1).getSelectedRoute(), is(route(0, sp(-1), path(2, 0))));
        collector.checkThat(state.get(2).getSelectedRoute(), is(route(0, sp(0), path(0))));
        collector.checkThat(state.get(3).getSelectedRoute(), is(route(0, sp(-2), path(1, 0))));
    }

    @Test(timeout = 2000)
    public void
    changeProtocol_OfNode2ToD1R1OnInstant3WhileSimulatingNetwork3ForDestination0_MessageCountIs14AndDetectedOnce()
            throws Exception {
        State state = State.create(factory.network(3), 0, new BGPProtocol());   // start all with the BGP protocol
        new NodeSetFixedTimeProtocolChanger(engine, state, 3L, new D1R1Protocol(), new Node(2));

        MessageAndDetectionCountHandler handler = new MessageAndDetectionCountHandler();
        handler.register(engine.getEventGenerator());

        engine.simulate(state, 0);

        collector.checkThat(handler.getMessageCount(), is(14));
        collector.checkThat(handler.getDetectionCount(), is(1));
    }

}