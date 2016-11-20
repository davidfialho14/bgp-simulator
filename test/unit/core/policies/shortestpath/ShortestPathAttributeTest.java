package core.policies.shortestpath;

import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static wrappers.ShortestPathWrapper.spAttr;

public class ShortestPathAttributeTest {

    @Test
    public void compareTo_AttributeWithLength0WithInvalidAttribute_Less() throws Exception {
        assertThat(spAttr(0).compareTo(invalidAttr()), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength1WithAttributeWithLength2_Less() throws Exception {
        assertThat(spAttr(1).compareTo(spAttr(2)), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength2WithAttributeWithLength1_Greater() throws Exception {
        assertThat(spAttr(2).compareTo(spAttr(1)), greaterThan(0));
    }

}