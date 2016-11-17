package v2.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static v2.stubs.Stubs.stubLabel;
import static v2.stubs.Stubs.stubLink;
import static v2.wrappers.TopologyWrapper.router;


public class TopologyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Topology topology;

    @Before
    public void setUp() throws Exception {
        topology = new Topology(null);  // the policy is not important for this test suite
    }

    @Test
    public void addRouter_RouterWithId0_ContainsRouterWithId0() throws Exception {
        topology.addRouter(router(0));

        assertThat(topology.getIds(), containsInAnyOrder(0));
    }

    @Test
    public void addRouter_RoutersWithIds0And1_ContainsRoutersWithIds0And1() throws Exception {
        topology.addRouter(router(0));
        topology.addRouter(router(1));

        assertThat(topology.getIds(), containsInAnyOrder(0, 1));
    }

    @Test
    public void addRouter_RouterWithId0Twice_SecondAddRouterReturnsFalse() throws Exception {
        topology.addRouter(router(0));

        assertThat(topology.addRouter(router(0)), is(false));
    }

    @Test
    public void link_Router0ToRouter1BothAlreadyAddedToTheTopology_ContainsLinkBetweenRouter0AndRouter1() throws Exception {
        Router router0 = router(0);
        Router router1 = router(1);
        topology.addRouter(router0);
        topology.addRouter(router1);

        topology.link(router0, router1, stubLabel());

        assertThat(topology.getLinks(), containsInAnyOrder(stubLink(router0, router1)));
    }

    @Test
    public void link_Router0ToRouter1ButRouter0WasNotAddedToTheTopology_Router0IsAddedAndLinked() throws Exception {
        Router router1 = router(1);
        topology.addRouter(router1);

        topology.link(router(0), router1, stubLabel());
    }

    @Test
    public void link_Router0ToRouter1ButRouter1WasNotAddedToTheTopology_ThrowsRouterNotFoundException() throws Exception {
        Router router0 = router(0);
        Router router1 = router(1);
        topology.addRouter(router0);

        topology.link(router0, router1, stubLabel());

        assertThat(topology.getRouters(), hasItem(router1));
        assertThat(topology.getLinks(), containsInAnyOrder(stubLink(router0, router1)));
    }

    @Test
    public void link_Router0ToRouter1Twice_ContainsOnlyOneLink() throws Exception {
        Router router0 = router(0);
        Router router1 = router(1);
        topology.addRouter(router0);
        topology.addRouter(router1);

        topology.link(router0, router1, stubLabel());
        topology.link(router0, router1, stubLabel());

        assertThat(topology.getLinks().size(), is(1));
    }

    @Test
    public void link_Router0ToRouter0_ContainsLinkFromRouter0ToRouter0() throws Exception {
        Router router0 = router(0);
        topology.addRouter(router0);

        topology.link(router0, router0, stubLabel());

        assertThat(topology.getLinks(), containsInAnyOrder(stubLink(router0, router0)));
    }

    @Test
    public void linkCount_OfTopologyWith1Link1_Is1() throws Exception {
        Router router0 = router(0);
        Router router1 = router(1);
        topology.addRouter(router0);
        topology.addRouter(router1);

        topology.link(router0, router1, stubLabel());

        assertThat(topology.getLinkCount(), is(1));
    }

    @Test
    public void linkCount_OfTopologyWith2Links_Is2() throws Exception {
        Router router0 = router(0);
        Router router1 = router(1);
        Router router2 = router(2);
        topology.addRouter(router0);
        topology.addRouter(router1);
        topology.addRouter(router2);

        topology.link(router0, router1, stubLabel());
        topology.link(router1, router2, stubLabel());

        assertThat(topology.getLinkCount(), is(2));
    }

}