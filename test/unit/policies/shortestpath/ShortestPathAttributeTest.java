package policies.shortestpath;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static policies.InvalidAttribute.invalid;
import static wrappers.ShortestPathWrapper.spattribute;

public class ShortestPathAttributeTest {

    @Test
    public void compareTo_AttributeWithLength0WithInvalidAttribute_Less() throws Exception {
        assertThat(spattribute(0).compareTo(invalid()), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength1WithAttributeWithLength2_Less() throws Exception {
        assertThat(spattribute(1).compareTo(spattribute(2)), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength2WithAttributeWithLength1_Greater() throws Exception {
        assertThat(spattribute(2).compareTo(spattribute(1)), greaterThan(0));
    }

}