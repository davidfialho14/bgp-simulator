package simulation;

import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import policies.gaorexford.GaoRexfordPolicy;
import protocols.BGPProtocol;
import simulation.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.GaoRexfordWrapper.*;
import static wrappers.PathWrapper.path;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

/*
    Allow duplicates in order to make the tests easier to understand without having to look for the network or
    the expected tables elsewhere.
 */
@SuppressWarnings("Duplicates")
public class EngineBGPAndGaoRexfordTest extends SimulateEngineTest {

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
        protocol = new BGPProtocol();
    }

    private static Network network0 = network(new GaoRexfordPolicy(),
            link(from(0), to(1), customerLabel()),
            link(from(1), to(0), providerLabel())
    );
    
    @Test(timeout = 2000)
    public void simulate_Network0ForDestination0_Converges() throws Exception {
        State state = State.create(network0, protocol);

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),
                destination(0), invalidRoute(), providerRoute(path(0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network0ForDestination1_Converges() throws Exception {
        State state = State.create(network0, protocol);

        engine.simulate(state, 1);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),
                destination(1), selfRoute(),    invalidRoute()
        )));
    }

    private static Network network1 = network(new GaoRexfordPolicy(),
            link(from(0), to(1), customerLabel()),
            link(from(1), to(0), providerLabel()),
            link(from(2), to(1), customerLabel()),
            link(from(1), to(2), providerLabel())
    );

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination0_Converges() throws Exception {
        State state = State.create(network1, protocol);

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(0), invalidRoute(), providerRoute(path(0)), invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(0), invalidRoute(), invalidRoute()
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination1_Converges() throws Exception {
        State state = State.create(network1, protocol);

        engine.simulate(state, 1);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(1), selfRoute(),    invalidRoute(),         invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network1ForDestination2_Converges() throws Exception {
        State state = State.create(network1, protocol);

        engine.simulate(state, 2);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(2), invalidRoute(), invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    providerLink(1, 0),     providerLink(1, 2),
                destination(2), invalidRoute(), invalidRoute(),         providerRoute(path(2))
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 1),
                destination(2), selfRoute(),    invalidRoute()
        )));
    }

    private static Network network2 = network(new GaoRexfordPolicy(),
            link(from(0), to(1), customerLabel()),
            link(from(1), to(2), customerLabel()),
            link(from(2), to(0), customerLabel())
    );

    @Test(timeout = 2000)
    public void simulate_Network2ForDestination0_Converges() throws Exception {
        State state = State.create(network2, protocol);

        engine.simulate(state, 0);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(0), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(0), invalidRoute(), customerRoute(path(2, 0))
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(0), invalidRoute(), customerRoute(path(0))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network2ForDestination1_Converges() throws Exception {
        State state = State.create(network2, protocol);

        engine.simulate(state, 1);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(1), invalidRoute(), customerRoute(path(1))
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(1), selfRoute(),    invalidRoute()
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(1), invalidRoute(), customerRoute(path(0, 1))
        )));
    }

    @Test(timeout = 2000)
    public void simulate_Network2ForDestination2_Converges() throws Exception {
        State state = State.create(network2, protocol);

        engine.simulate(state, 2);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),    customerLink(0, 1),
                destination(2), invalidRoute(), customerRoute(path(1, 2))
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    customerLink(1, 2),
                destination(2), invalidRoute(), customerRoute(path(2))
        )));

        assertThat(state.get(new Node(2)).getTable(), is( table(
                                selfLink(2),    customerLink(2, 0),
                destination(2), selfRoute(),    invalidRoute()
        )));
    }
}