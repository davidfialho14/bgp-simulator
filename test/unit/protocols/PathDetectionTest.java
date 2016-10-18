package protocols;

import core.Path;
import core.Route;
import core.topology.Link;
import core.topology.Node;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static stubs.Stubs.stubLink;
import static stubs.Stubs.stubRoute;
import static wrappers.PathWrapper.path;

@RunWith(Parameterized.class)
public class PathDetectionTest {

    private Detection detection;
    private int learnedAttribute;
    private Path learnedPath;
    private int alternativeAttribute;
    private Path alternativePath;
    private boolean expectedResult;

    @Before
    public void setUp() throws Exception {
        detection = new PathDetection();
    }

    public PathDetectionTest(int learnedAttribute, Path learnedPath, int alternativeAttribute, Path alternativePath,
                             boolean expectedResult) {
        this.learnedAttribute = learnedAttribute;
        this.learnedPath = learnedPath;
        this.alternativeAttribute = alternativeAttribute;
        this.alternativePath = alternativePath;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "with learned route ({0}, {1}) and alternative route ({2}, {3}) returns {4}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 1, path(2, 1, 0), 0, path(0), true },
                { 1, path(2, 1, 0), 1, path(0), false },
                { 1, path(2, 1, 3), 0, path(0), false },
                { 1, path(2, 1, 3, 0), 0, path(0), false },
                { 0, path(2, 1, 0), 1, path(0), false },
        });
    }

    @Test
    public void isPolicyDispute() throws Exception {
        // link from which the route was learned
        // detecting node ID: 1
        // neighbor ID: 2
        Link link = stubLink(1, 2);

        // destination node for the routes
        Node destination = Node.newNode(0);

        Route learnedRoute = stubRoute(destination, learnedAttribute, learnedPath);
        Route alternativeRoute = stubRoute(destination, alternativeAttribute, alternativePath);

        assertThat(detection.isPolicyDispute(link, learnedRoute, alternativeRoute, null), is(expectedResult));
    }

}