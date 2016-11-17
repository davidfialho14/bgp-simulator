package core;

import core.topology.ConnectedNode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static core.InvalidPath.invalidPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static wrappers.PathWrapper.path;
import static wrappers.topology.TopologyWrapper.anyNode;

public class PathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidPath_AlwaysReturnsTheSameInstance() throws Exception {
        assert invalidPath() == invalidPath();  // assert the reference is the same
    }

    @Test
    public void invalidPath_ReturnsInvalidPathAttribute() throws Exception {
        assertThat(invalidPath(), is(invalidPath()));
    }

    @Test
    public void compareTo_TwoEmptyPaths_Equal() throws Exception {
        assertThat(path().compareTo(path()), equalTo(0));
    }

    @Test
    public void compareTo_PathWithNode0ToEmptyPath_Greater() throws Exception {
        assertThat(path(0).compareTo(path()), greaterThan(0));
    }

    @Test
    public void compareTo_EmptyPathToPathWithNode0_Less() throws Exception {
        assertThat(path().compareTo(path(0)), lessThan(0));
    }

    @Test
    public void compareTo_PathWithNode1ToPathWithNode1_Equal() throws Exception {
        assertThat(path(1).compareTo(path(1)), equalTo(0));
    }

    @Test
    public void compareTo_PathWithNode1ToPathWithNode2_Equal() throws Exception {
        assertThat(path(1).compareTo(path(2)), equalTo(0));
    }

    @Test
    public void compareTo_PathWithNodes0And1ToPathWithNode1_Greater() throws Exception {
        assertThat(path(0, 1).compareTo(path(1)), greaterThan(0));
    }

    @Test
    public void contains_InvalidPath_ReturnsFalse() throws Exception {
        assertThat(invalidPath().contains(anyNode()), is(false));
    }

    @Test
    public void getPathAfter_Node1WithEmptyPath_EmptyPath() throws Exception {
        assertThat(path().getPathAfter(new ConnectedNode(1)), is(path()));
    }

    @Test
    public void getPathAfter_Node1ForPathWithNode1And0_PathWithNode0() throws Exception {
        assertThat(path(1, 0).getPathAfter(new ConnectedNode(1)), is(path(0)));
    }

    @Test
    public void getPathAfter_Node1ForPathWithNode1_EmptyPath() throws Exception {
        assertThat(path(1).getPathAfter(new ConnectedNode(1)), is(path()));
    }

    @Test
    public void getPathAfter_Node0ForPathWithNode1_EmptyPath() throws Exception {
        assertThat(path(1).getPathAfter(new ConnectedNode(0)), is(path()));
    }

    @Test
    public void getPathAfter_Node1ForPathWithNode2And1And4And0_PathWithNode4And0() throws Exception {
        assertThat(path(2, 1, 4, 0).getPathAfter(new ConnectedNode(1)), is(path(4, 0)));
    }

    @Test
    public void copy_WithInvalidPath_ThrowsIllegalArgumentException() throws Exception {
        assertThat(Path.copy(invalidPath()), is(invalidPath()));
    }
}