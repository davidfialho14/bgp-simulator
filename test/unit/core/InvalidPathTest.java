package core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static core.InvalidPath.invalidPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class InvalidPathTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void compareTo_InvalidPathWithInvalidPath_Equal() throws Exception {
        assertThat(invalidPath().compareTo(invalidPath()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidPathWithDummyPath_Greater() throws Exception {
        assertThat(invalidPath().compareTo(new Path()), greaterThan(0));
    }

    @Test
    public void contains_AnyNode_ReturnsFalse() throws Exception {
        assertThat(invalidPath().contains(null), is(false));
    }

    @Test
    public void add_AnyNode_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().add(null);
    }

    @Test
    public void getPathAfter_AnyNode_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().getPathAfter(null);
    }

    @Test
    public void getSubPathBefore_AnyNode_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().getSubPathBefore(null);
    }

    @Test
    public void iterator_ForInvalidPath_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().iterator();
    }

    @Test
    public void stream_AnyNode_ThrowsUnsupportedOperationException() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        invalidPath().stream();
    }

}