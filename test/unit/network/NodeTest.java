package network;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static wrappers.StubWrapper.stubLink;

public class NodeTest {

    @Test
    public void
    addOutLink_FromNode0ToNode1WithDummyLabel_NodeContainsOutLinkFromNode0ToNode1WithDummyLabel() throws Exception {
        Node node = new Node(0);
        node.addOutLink(stubLink(0, 1));

        assertThat(node.getOutLinks(), containsInAnyOrder(stubLink(0, 1)));
    }

    @Test
    public void
    addInLink_FromNode0ToNode1WithDummyLabel_NodeContainsInLinkFromNode0ToNode1WithDummyLabel() throws Exception {
        Node node = new Node(1);
        node.addInLink(stubLink(0, 1));

        assertThat(node.getInLinks(), containsInAnyOrder(stubLink(0, 1)));
    }
}