package policies.shortestpath;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static core.InvalidAttribute.invalidAttr;
import static wrappers.ShortestPathWrapper.sp;

public class ShortestPathAttributeTest {

    @Test
    public void compareTo_AttributeWithLength0WithInvalidAttribute_Less() throws Exception {
        assertThat(sp(0).compareTo(invalidAttr()), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength1WithAttributeWithLength2_Less() throws Exception {
        assertThat(sp(1).compareTo(sp(2)), lessThan(0));
    }

    @Test
    public void compareTo_AttributeWithLength2WithAttributeWithLength1_Greater() throws Exception {
        assertThat(sp(2).compareTo(sp(1)), greaterThan(0));
    }

}