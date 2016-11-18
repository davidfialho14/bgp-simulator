package v2.core.policies;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static v2.core.InvalidAttribute.invalidAttr;
import static v2.stubs.Stubs.stubAttr;

public class InvalidAttributeTest {

    @Test
    public void compareTo_InvalidAttributeWithInvalidAttribute_Equal() throws Exception {
        assertThat(invalidAttr().compareTo(invalidAttr()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidAttributeWithDummyAttribute_Greater() throws Exception {
        assertThat(invalidAttr().compareTo(stubAttr(0)), greaterThan(0));

    }
}