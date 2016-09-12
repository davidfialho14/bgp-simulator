package core;

import core.schedulers.FIFOScheduler;
import core.topology.ConnectedNode;
import factories.GaoRexfordTopologyFactory;
import factories.TopologyFactory;
import org.junit.Before;
import org.junit.Test;
import protocols.BGPProtocol;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.GaoRexfordWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

/*
    Allow duplicates in order to make the tests easier to understand without having to look for the topology or
    the expected tables elsewhere.
 */
@SuppressWarnings("Duplicates")
public class EngineBGPAndGaoRexfordTest extends SimulateEngineTest {

    private TopologyFactory factory = new GaoRexfordTopologyFactory();

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new BGPProtocol();
    }

    @Test
    public void simulate_Network0ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.topology(0), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),
                destination(0), invalidRoute(), providerRoute(path(0))
        )));
    }

    @Test
    public void simulate_Network0ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        core.State state = core.State.create(factory.topology(0), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),
                destination(1), selfRoute(),    invalidRoute()
        )));
    }

    @Test
    public void simulate_Network1ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(0), invalidRoute(), providerRoute(path(0)), invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(0), invalidRoute(), invalidRoute()
        )));
    }

    @Test
    public void simulate_Network1ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        core.State state = core.State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(1), selfRoute(),    invalidRoute(),         invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));
    }

    @Test
    public void simulate_Network1ForDestination2_Converges() throws Exception {
        int destinationId = 2;
        core.State state = core.State.create(factory.topology(1), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(2), invalidRoute(), invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(2), invalidRoute(), invalidRoute(),         providerRoute(path(2))
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(2), selfRoute(),    invalidRoute()
        )));
    }

    @Test
    public void simulate_Network2ForDestination0_Converges() throws Exception {
        int destinationId = 0;
        core.State state = core.State.create(factory.topology(2), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(0), invalidRoute(), customerRoute(path(2, 0))
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(0), invalidRoute(), customerRoute(path(0))
        )));
    }

    @Test
    public void simulate_Network2ForDestination1_Converges() throws Exception {
        int destinationId = 1;
        core.State state = core.State.create(factory.topology(2), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(1), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(1), invalidRoute(), customerRoute(path(0, 1))
        )));
    }

    @Test
    public void simulate_Network2ForDestination2_Converges() throws Exception {
        int destinationId = 2;
        core.State state = core.State.create(factory.topology(2), destinationId, protocol);

        engine.simulate(state);

        assertThat(state.get(new ConnectedNode(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(2), invalidRoute(), customerRoute(path(1, 2))
        )));

        assertThat(state.get(new ConnectedNode(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(2), invalidRoute(), customerRoute(path(2))
        )));

        assertThat(state.get(new ConnectedNode(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(2), selfRoute(),    invalidRoute()
        )));
    }
}