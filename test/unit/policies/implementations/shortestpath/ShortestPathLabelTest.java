package policies.implementations.shortestpath;

import network.Link;
import org.junit.Test;

import static network.Factory.createRandomLink;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static policies.InvalidAttribute.invalid;

public class ShortestPathLabelTest {

    @Test
    public void extend_SPAttributeWithLength1ThroughLabelLength2_SPAttributeWithLength3() throws Exception {
        ShortestPathLabel label = new ShortestPathLabel(1);
        Link link = new Link(null, null, null);
        ShortestPathAttribute attribute = new ShortestPathAttribute(2);

        assertThat(label.extend(link, attribute), is(new ShortestPathAttribute(3)));
    }

    /**
     * Returns an arbitrary ShortestPathLabel.
     */
    private ShortestPathLabel anyShortestPathLabel() {
        return new ShortestPathLabel(0);
    }

    @Test
    public void extend_InvalidAttributeThroughAnyLabel_InvalidAttribute() throws Exception {
        ShortestPathLabel label = anyShortestPathLabel();
        Link link = createRandomLink();

        assertThat(label.extend(link, invalid()), is(invalid()));
    }
}