package simulation.implementations.handlers;

// TODO EventHandler
//public class MessageAndDetectionCountHandlerTest {
//
//    private MessageAndDetectionCountHandler eventHandler;
//    private Engine engine;
//
//    @Before
//    public void setUp() throws Exception {
//        eventHandler = new MessageAndDetectionCountHandler();
//        engine = new Engine.Builder(
//                new ShortestPathPolicy(),
//                new FIFOScheduler())
//                .eventHandler(eventHandler)
//                .build();
//    }
//
//    @Test
//    public void messageCount_ForTopology0WithBGP_Is1() throws Exception {
//        Network network0 = network(new ShortestPathPolicy(),
//                link(from(0), to(1), label(1)));
//
//        engine.simulate(network0, new BGPProtocol());
//
//        assertThat(eventHandler.getMessageCount(), is(1));
//    }
//
//    @Test
//    public void messageCount_ForTopology1_Is4() throws Exception {
//        Network network1 = network(new ShortestPathPolicy(),
//                link(from(0), to(1), label(1)),
//                link(from(1), to(2), label(1)),
//                link(from(0), to(2), label(0)));
//
//        engine.simulate(network1, new BGPProtocol());
//
//        assertThat(eventHandler.getMessageCount(), is(4));
//    }
//
//    private static Network network3 = network(new ShortestPathPolicy(),
//            link(from(1), to(0), label(0)),
//            link(from(2), to(0), label(0)),
//            link(from(3), to(0), label(0)),
//            link(from(1), to(2), label(-1)),
//            link(from(2), to(3), label(1)),
//            link(from(3), to(1), label(-2)));
//
//    @Test
//    public void messageCount_ForTopology3WithD1R1_Is() throws Exception {
//        engine.simulate(network3, new D1R1Protocol(), 0);
//
//        assertThat(eventHandler.getMessageCount(), is(13));
//    }
//
//    @Test
//    public void detectionCount_ForTopology3WithD1R1_Is2() throws Exception {
//        engine.simulate(network3, new D1R1Protocol(), 0);
//
//        assertThat(eventHandler.getDetectionCount(), is(2));
//    }
//}