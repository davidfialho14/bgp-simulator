package network;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static wrappers.DummyWrapper.dummyLink;

public class NodeTest {

    @Test
    public void
    addOutLink_FromNode0ToNode1WithDummyLabel_NodeContainsOutLinkFromNode0ToNode1WithDummyLabel() throws Exception {
        Node node = new Node(0);
        node.addOutLink(dummyLink(0, 1));

        assertThat(node.getOutLinks(), containsInAnyOrder(dummyLink(0, 1)));
    }

    @Test
    public void
    addInLink_FromNode0ToNode1WithDummyLabel_NodeContainsInLinkFromNode0ToNode1WithDummyLabel() throws Exception {
        Node node = new Node(1);
        node.addInLink(dummyLink(0, 1));

        assertThat(node.getInLinks(), containsInAnyOrder(dummyLink(0, 1)));
    }
}