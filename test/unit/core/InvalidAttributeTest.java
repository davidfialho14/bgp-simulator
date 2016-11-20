package core;

import org.junit.Test;
import stubs.Stubs;

import static core.InvalidAttribute.invalidAttr;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

public class InvalidAttributeTest {

    @Test
    public void compareTo_InvalidAttributeWithInvalidAttribute_Equal() throws Exception {
        assertThat(invalidAttr().compareTo(invalidAttr()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidAttributeWithDummyAttribute_Greater() throws Exception {
        assertThat(invalidAttr().compareTo(Stubs.stubAttr(0)), greaterThan(0));

    }
}