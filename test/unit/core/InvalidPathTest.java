package core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static core.InvalidPath.invalidPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static wrappers.TopologyWrapper.router;

public class InvalidPathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static Router anyRouter() {
        return router(0);
    }

    @Test
    public void compareTo_InvalidPathWithInvalidPath_Equal() throws Exception {
        assertThat(invalidPath().compareTo(invalidPath()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidPathWithDummyPath_Greater() throws Exception {
        assertThat(invalidPath().compareTo(new Path()), greaterThan(0));
    }

    @Test
    public void contains_AnyRouter_ReturnsFalse() throws Exception {
        assertThat(invalidPath().contains(anyRouter()), is(false));
    }

    @Test
    public void add_AnyRouter_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().add(anyRouter());
    }

    @Test
    public void getPathAfter_AnyRouter_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().getPathAfter(anyRouter());
    }

    @Test
    public void getSubPathBefore_AnyRouter_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().getSubPathBefore(anyRouter());
    }

    @Test
    public void iterator_ForInvalidPath_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().iterator();
    }

    @Test
    public void stream_AnyRouter_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().stream();
    }

}