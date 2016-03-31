package simulation.implementations.policies.shortestpath;

import network.Link;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShortestPathLabelTest {

    ShortestPathAttributeFactory attributeFactory;

    @Before
    public void setUp() throws Exception {
        attributeFactory = new ShortestPathAttributeFactory();
    }

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
    public void extend_InvalidSPAttributeThroughLabelWithAnyLength_InvalidSPAttribute() throws Exception {
        ShortestPathLabel label = anyShortestPathLabel();
        Link link = new Link(null, null, null);

        assertThat(label.extend(link, attributeFactory.createInvalid()), is(attributeFactory.createInvalid()));
    }
}