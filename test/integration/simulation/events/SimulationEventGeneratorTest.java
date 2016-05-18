package simulation.events;

import network.Link;
import network.Network;
import network.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import policies.shortestpath.ShortestPathPolicy;
import protocols.BGPProtocol;
import simulation.Engine;
import simulation.State;
import simulation.schedulers.FIFOScheduler;

import static org.mockito.Mockito.*;
import static simulation.Route.invalidRoute;
import static wrappers.PathWrapper.path;
import static wrappers.ShortestPathWrapper.*;
import static wrappers.network.FromNodeElement.from;
import static wrappers.network.LinkElement.link;
import static wrappers.network.NetworkWrapper.network;
import static wrappers.network.ToNodeElement.to;

@RunWith(MockitoJUnitRunner.class)
public class SimulationEventGeneratorTest {

    private Engine engine;

    @Before
    public void setUp() throws Exception {
        engine = new Engine(new FIFOScheduler());
    }

    @Test
    public void onLearned_NetworkWithOnlyLinkFrom0To1_CalledOnceWithLink0To1AndRoute1WithPathWithNode1() throws Exception {
        LearnListener listener = mock(LearnListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        int destinationId = 1;
        State state = State.create(network0, destinationId, new BGPProtocol());
        engine.getEventGenerator().addLearnListener(listener);

        engine.simulate(state);

        verify(listener, times(1)).onLearned(new LearnEvent(new Link(0, 1, splabel(1)), sproute(1, 1, path(1))));
    }

    @Test
    public void onImported_NetworkWithOnlyLinkFrom0To1_CalledOnceWithLink0To1AndRoute0EmptyPath() throws Exception {
        ImportListener listener = mock(ImportListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        int destinationId = 1;
        State state = State.create(network0, destinationId, new BGPProtocol());
        engine.getEventGenerator().addImportListener(listener);

        engine.simulate(state);

        verify(listener, times(1)).onImported(new ImportEvent(sproute(1, 0, path()), new Link(0, 1, splabel(1))));
    }

    @Test
    public void onSelected_NetworkWithOnlyLinkFrom0To1_CalledOnceWithPreviousInvalidRouteAndRoute1WithPathWithNode1()
            throws Exception {
        SelectListener listener = mock(SelectListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        int destinationId = 1;
        State state = State.create(network0, destinationId, new BGPProtocol());
        engine.getEventGenerator().addSelectListener(listener);

        engine.simulate(state);

        verify(listener, times(1)).onSelected(
                new SelectEvent(new Node(0), invalidRoute(new Node(1)), sproute(1, 1, path(1))));
    }

    @Test
    public void onExported_NetworkWithOnlyLinkFrom0To1_CalledOnceWithLink0To1AndRoute0AndEmptyPath()
            throws Exception {
        ExportListener listener = mock(ExportListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        int destinationId = 1;
        State state = State.create(network0, destinationId, new BGPProtocol());
        engine.getEventGenerator().addExportListener(listener);

        engine.simulate(state);

        verify(listener, times(1)).onExported(new ExportEvent(new Link(0, 1, splabel(1)), sproute(1, 0, path())));
    }
}