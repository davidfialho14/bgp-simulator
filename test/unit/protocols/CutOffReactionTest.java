package protocols;

import org.junit.Before;
import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static stubs.Stubs.stubAttr;
import static stubs.Stubs.stubLink;

public class CutOffReactionTest {

    private Reaction reaction;
    private final int nodeId = 0;   // ID of the node that holds the reaction

    @Before
    public void setUp() throws Exception {
        reaction = new CutOffReaction();
    }

    private void markBadNeighbor(int neighborId) {
        // the learned and alternative routes parameters are ignored by this reaction implementation
        reaction.detectionInfo(stubLink(nodeId, neighborId), null, null);
    }

    @Test
    public void extendAttr0WithLinkToNeighbor1_ThereAreNoBadNeighbors_ReturnsAttr1() throws Exception {
        assertThat(reaction.extend(stubAttr(0), stubLink(nodeId, 1)), is(stubAttr(1)));
    }

    @Test
    public void extendAttr0WithLinkToNeighbor1_Neighbor1And2AreMarkedBad_ReturnsInvalidAttr() throws Exception {
        markBadNeighbor(1);
        markBadNeighbor(2);

        assertThat(reaction.extend(stubAttr(0), stubLink(nodeId, 1)), is(invalidAttr()));
    }

    @Test
    public void extendAttr0WithLinkToNeighbor3_Neighbor1And2AreMarkedBad_ReturnsAttr1() throws Exception {
        markBadNeighbor(1);
        markBadNeighbor(2);

        assertThat(reaction.extend(stubAttr(0), stubLink(nodeId, 3)), is(stubAttr(1)));
    }

    @Test
    public void extendAttr0WithLinkToNeighbor1_AfterResettingWithNeighbor1MarkedBad_ReturnsAttr1() throws Exception {
        markBadNeighbor(1);
        markBadNeighbor(2);

        reaction.reset();

        assertThat(reaction.extend(stubAttr(0), stubLink(nodeId, 1)), is(stubAttr(1)));
    }

}