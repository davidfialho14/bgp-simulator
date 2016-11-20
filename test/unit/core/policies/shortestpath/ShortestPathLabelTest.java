package core.policies.shortestpath;

import org.junit.Test;

import static core.InvalidAttribute.invalidAttr;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static wrappers.ShortestPathWrapper.spAttr;
import static wrappers.ShortestPathWrapper.spLabel;

public class ShortestPathLabelTest {

    @Test
    public void extend_AttributeWithLength2WithLabelWithLength1_SPAttributeWithLength3() throws Exception {
        assertThat(spLabel(1).extend(null, spAttr(2)), is(new ShortestPathAttribute(3)));
    }

    @Test
    public void extend_InvalidAttributeWithAnyLabel_InvalidAttribute() throws Exception {
        ShortestPathLabel anySPLabel = spLabel(0);
        assertThat(anySPLabel.extend(null, invalidAttr()), is(invalidAttr()));
    }
}