package policies;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static policies.InvalidAttribute.invalid;

public class InvalidAttributeTest {

    @Test
    public void compareTo_InvalidAttributeWithInvalidAttribute_Equals() throws Exception {
        assertThat(invalid().compareTo(invalid()), equalTo(0));
    }

    @Test
    public void compareTo_InvalidAttributeWithDummyAttribute_Equals() throws Exception {
        assertThat(invalid().compareTo(new DummyAttribute()), greaterThan(0));
    }
}