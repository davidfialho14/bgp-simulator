package policies;

import stubs.StubAttribute;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static core.InvalidAttribute.invalidAttr;

public class InvalidAttributeTest {

    @Test
    public void compareTo_InvalidAttributeWithInvalidAttribute_Equal() throws Exception {
        assertThat(invalidAttr().compareTo(invalidAttr()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidAttributeWithDummyAttribute_Greater() throws Exception {
        assertThat(invalidAttr().compareTo(new StubAttribute()), greaterThan(0));
    }
}