package policies.implementations.shortestpath;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class ShortestPathAttributeTest {

    @Test
    public void compareTo_TwoInvalidAttributes_Equal() throws Exception {
        ShortestPathAttribute attribute1 = ShortestPathAttribute.createInvalidShortestPath();
        ShortestPathAttribute attribute2 = ShortestPathAttribute.createInvalidShortestPath();

        assertThat(attribute1.compareTo(attribute2), equalTo(0));
    }

    @Test
    public void compareTo_InvalidAttributeWithValidAttribute_Greater() throws Exception {
        ShortestPathAttribute invalidAttribute = ShortestPathAttribute.createInvalidShortestPath();
        ShortestPathAttribute validAttribute = new ShortestPathAttribute(0);

        assertThat(invalidAttribute.compareTo(validAttribute), greaterThan(0));
    }

    @Test
    public void compareTo_ValidAttributeWithInvalidAttribute_Lesser() throws Exception {
        ShortestPathAttribute validAttribute = new ShortestPathAttribute(0);
        ShortestPathAttribute invalidAttribute = ShortestPathAttribute.createInvalidShortestPath();

        assertThat(validAttribute.compareTo(invalidAttribute), lessThan(0));
    }

    @Test
    public void compareTo_Length1WithLength2_Lesser() throws Exception {
        ShortestPathAttribute attribute1 = new ShortestPathAttribute(1);
        ShortestPathAttribute attribute2 = new ShortestPathAttribute(2);

        assertThat(attribute1.compareTo(attribute2), lessThan(0));
    }

    @Test
    public void compareTo_Length2WithLength1_Greater() throws Exception {
        ShortestPathAttribute attribute2 = new ShortestPathAttribute(2);
        ShortestPathAttribute attribute1 = new ShortestPathAttribute(1);

        assertThat(attribute2.compareTo(attribute1), greaterThan(0));
    }

}