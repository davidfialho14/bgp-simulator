package networks;

import factories.ShortestPathNetworkFactory;
import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import protocols.BGPProtocol;
import core.Engine;
import core.State;
import core.schedulers.FIFOScheduler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.PathWrapper.path;
import static wrappers.ShortestPathWrapper.splink;
import static wrappers.ShortestPathWrapper.sproute;
import static wrappers.routetable.DestinationElement.destination;
import static wrappers.routetable.OutLinkElement.selfLink;
import static wrappers.routetable.RouteElement.invalidRoute;
import static wrappers.routetable.RouteTableWrapper.table;

public class ShortestPathNetwork0Test extends ShortestPathNetworkTest {

    private Engine engine;
    private Network network;

    @Before
    public void setUp() throws Exception {
        network = new ShortestPathNetworkFactory().network(0);
    }

    @Test(timeout = 2000)
    public void simulate_BGPProtocolAndFIFOScheduler_Converges() throws Exception {
        engine = new Engine(new FIFOScheduler());
        int destinationId = 0;
        State state = State.create(network, destinationId, new BGPProtocol());

        engine.simulate(state);

        assertThat(state.get(new Node(0)).getTable(), is( table(
                                selfLink(0),
                destination(0), sproute(0, path())
        )));

        assertThat(state.get(new Node(1)).getTable(), is( table(
                                selfLink(1),    splink(1, 0, 1),
                destination(0), invalidRoute(), sproute(1, path(0))
        )));
    }

    @Test(timeout = 2000)
    public void
    simulate_BGPProtocolAndFIFOSchedulerInsertLink1To9WithLength0AtTime1_Node1SelectsRouteWithAttr1AndPathWithNode0()
            throws Exception {
        engine = new Engine(new FIFOScheduler());
        int destinationId = 0;
        State state = State.create(network, destinationId, new BGPProtocol());

        engine.simulate(state);

        assertThat(state.get(new Node(1)).getSelectedRoute(), is(sproute(0, 1, path(0))));
    }

    @Test(timeout = 2000)
    public void
    simulate_BGPProtocolAndFIFOSchedulerInsertLink1To9WithLength0AtTime1_Node1LearnsRouteFromNewLink()
            throws Exception {
        // TODO Link Inserter
//        engine = new Engine(new FIFOScheduler());
//                .linkInserter(new FixedTimeLinkInserter(new Link(1, 0, splabel(0)), 1L))
//                .build();
//
//        engine.simulate(network, new BGPProtocol(), 0);
//
//        assertThat(state.get(new Node(0)).getTable(), is( table(
//                                selfLink(0),
//                destination(0), sproute(0, path())
//        )));
//
//        assertThat(state.get(new Node(1)).getTable(), is( table(
//                                selfLink(1),    splink(1, 0, 1),     splink(1, 0, 0),
//                destination(0), invalidRoute(), sproute(1, path(0)), sproute(0, path(0))
//        )));
    }

    @Test(timeout = 2000)
    public void
    simulate_BGPProtocolAndFIFOSchedulerInsertLink1To9WithLength0AtTime1_Node1PrefersNewLink() throws Exception {
        // TODO Link Inserter
//        engine = new Engine(new FIFOScheduler());
//                .linkInserter(new FixedTimeLinkInserter(new Link(1, 0, splabel(0)), 1L))
//                .build();
//
//        engine.simulate(network, new BGPProtocol(), 0);
//
//        assertThat(engine.getSelectedRoute(new Node(1), new Node(0)), is(sproute(0, 0, path(0))));
    }
}
