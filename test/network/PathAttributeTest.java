package network;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PathAttributeTest {

    @Test
    public void createInvalid_AlwaysReturnsTheSameInstance() throws Exception {
        PathAttribute invalid = PathAttribute.createInvalid();
        assert invalid == PathAttribute.createInvalid();
    }

    @Test
    public void createInvalid_ReturnsInvalidPathAttribute() throws Exception {
        assertThat(PathAttribute.createInvalid().isInvalid(), is(true));
    }

    @Test
    public void compareTo_TwoEmptyPathsCompared_Equal() throws Exception {
        PathAttribute emptyPath1 = new PathAttribute();
        PathAttribute emptyPath2 = new PathAttribute();

        assertThat(emptyPath1.compareTo(emptyPath2), equalTo(0));
    }

    @Test
    public void compareTo_PathWithOneNodeComparedEmptyPath_Greater() throws Exception {
        PathAttribute pathWithOneNode = new PathAttribute(Factory.createNode());
        PathAttribute emptyPath = new PathAttribute();

        assertThat(pathWithOneNode.compareTo(emptyPath), greaterThan(0));
    }

    @Test
    public void compareTo_EmptyPathComparedPathWithOneNode_Lesser() throws Exception {
        PathAttribute pathWithOneNode = new PathAttribute(Factory.createNode());
        PathAttribute emptyPath = new PathAttribute();

        assertThat(emptyPath.compareTo(pathWithOneNode), lessThan(0));
    }

    @Test
    public void compareTo_PathWithNode1ComparedPathWithNode1_Equal() throws Exception {
        PathAttribute path1WithNode1 = new PathAttribute(new Node(null, 1, null));
        PathAttribute path2WithNode1 = new PathAttribute(new Node(null, 1, null));

        assertThat(path1WithNode1.compareTo(path2WithNode1), equalTo(0));
    }

    @Test
    public void compareTo_PathWithNode1ComparedPathWithNode2_Equal() throws Exception {
        PathAttribute pathWithNode1 = new PathAttribute(new Node(null, 1, null));
        PathAttribute pathWithNode2 = new PathAttribute(new Node(null, 2, null));

        assertThat(pathWithNode1.compareTo(pathWithNode2), equalTo(0));
    }

    @Test
    public void compareTo_PathWithTwoNodes1And2ComparedPathWithNode1_Greater() throws Exception {
        Node[] nodes = Factory.createNodes(2);
        PathAttribute pathWithTwoNodes1And2 = new PathAttribute(nodes);
        PathAttribute pathWithNode1 = new PathAttribute(new Node(null, 1, null));

        assertThat(pathWithTwoNodes1And2.compareTo(pathWithNode1), greaterThan(0));
    }

}