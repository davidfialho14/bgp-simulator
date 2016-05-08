package simulation.events;

import network.Link;
import network.Network;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import policies.implementations.shortestpath.ShortestPathPolicy;
import protocols.implementations.BGPProtocol;
import simulation.Engine;
import simulation.State;
import simulation.implementations.schedulers.FIFOScheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void onLearned_NetworkWithOnlyLinkFrom0To1_CalledOnceWithLink0To1AndRoute1WithPath1() throws Exception {
        LearnListener listener = mock(LearnListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        State state = State.create(network0, new BGPProtocol());
        engine.getEventGenerator().addLearnListener(listener);

        engine.simulate(state, 1);

        verify(listener, times(1)).onLearned(new LearnEvent(new Link(0, 1, splabel(1)), sproute(1, 1, path(1))));
    }

    @Test
    public void onImported_NetworkWithOnlyLinkFrom0To1_CalledOnceWithLink0To1AndRoute0EmptyPath() throws Exception {
        ImportListener listener = mock(ImportListener.class);

        Network network0 = network(new ShortestPathPolicy(),
                link(from(0), to(1), label(1)));
        State state = State.create(network0, new BGPProtocol());
        engine.getEventGenerator().addImportListener(listener);

        engine.simulate(state, 1);

        verify(listener, times(1)).onImported(new ImportEvent(sproute(1, 0, path()), new Link(0, 1, splabel(1))));
    }
}