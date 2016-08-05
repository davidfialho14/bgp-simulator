package policies.shortestpath;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static core.InvalidAttribute.invalid;
import static wrappers.ShortestPathWrapper.anySPLabel;
import static wrappers.ShortestPathWrapper.sp;
import static wrappers.ShortestPathWrapper.splabel;

public class ShortestPathLabelTest {

    @Test
    public void extend_AttributeWithLength2WithLabelWithLength1_SPAttributeWithLength3() throws Exception {
        assertThat(splabel(1).extend(null, sp(2)), is(new ShortestPathAttribute(3)));
    }

    @Test
    public void extend_InvalidAttributeWithAnyLabel_InvalidAttribute() throws Exception {
        assertThat(anySPLabel().extend(null, invalid()), is(invalid()));
    }
}