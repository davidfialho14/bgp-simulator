package io;

import core.*;
import core.policies.gaorexford.GaoRexfordPolicy;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static core.Destination.newDestination;
import static core.policies.gaorexford.GRLabel.customerLabel;
import static io.AnycastReaderTest.IsDestinations.isDestinations;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(Parameterized.class)
public class AnycastReaderTest {

    // NOTE: for the tests we are using the Gao-Rexford policy. Therefore, the valid labels are C, R, and P
    //       for customer, peer, and provider relationships, respectively.
    private static Topology topology = new Topology(new GaoRexfordPolicy(), null);

    static {
        // IDs that are in the topology: 10 and 11
        topology.addRouter(new Router(10, 0, null));
        topology.addRouter(new Router(11, 0, null));
    }

    private String fileContent;
    private Destination[] expectedDestinations;

    public AnycastReaderTest(String fileContent, Destination[] expectedDestinations) {
        this.fileContent = fileContent;
        this.expectedDestinations = expectedDestinations;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Helper Methods
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Creates a reader for a fake file. The file content can be specified with strings. Each string
     * introduced will be treated as a line. It is not necessary to use the '\n' character.
     *
     * @param lines strings defining the lines (content) of the file.
     * @return reader for the fake file.
     */
    private static Reader fileWithLines(String... lines) {
        return new StringReader(StringUtils.join(lines, "\n"));
    }

    private static String file(String... lines) {
        return StringUtils.join(lines, "\n");
    }

    private static class Neighbor {
        private final int id;
        private final Label label;

        private Neighbor(int id, Label label) {
            this.id = id;
            this.label = label;
        }
    }

    private static Neighbor neighbor(int id, Label label) {
        return new Neighbor(id, label);
    }

    private static Destination destination(int id, Neighbor... neighbors) {
        Destination destination = newDestination(id);

        // Initialize the destination with the set neighbors
        for (Neighbor neighbor : neighbors) {
            Router neighborRouter = topology.getRouter(neighbor.id);

            if (neighborRouter == null) {
                throw new IllegalArgumentException(String.format("Neighbor ID %d is not in the set of IDs " +
                        "in the topology", neighbor.id));
            }

            destination.addInNeighbor(neighborRouter, neighbor.label);
        }

        return destination;
    }

    private static Destination[] destinations(Destination... destinations) {
        return destinations;
    }

    static class IsDestinations extends TypeSafeDiagnosingMatcher<Destination[]> {

        private Destination[] expectedDestinations;

        public IsDestinations(Destination[] destinations) {
            this.expectedDestinations = destinations;
        }

        public static Matcher<Destination[]> isDestinations(Destination[] destinations) {
            return new IsDestinations(destinations);
        }

        @Override
        protected boolean matchesSafely(Destination[] otherDestinations, Description description) {

            description.appendText("got ");
            appendDestinations(description, otherDestinations);

            if (!Arrays.equals(expectedDestinations, otherDestinations)) return false;

            // we can use either length - at this point we know that both arrays have the same length
            // otherwise the Arrays.equals() method would have returned false
            for (int i = 0; i < expectedDestinations.length; i++) {
                List<Link> inLinks = new ArrayList<>(expectedDestinations[i].getInLinks());
                List<Link> otherInLinks = new ArrayList<>(otherDestinations[i].getInLinks());

                if (!inLinks.equals(otherInLinks)) return false;
            }

            return true;
        }

        @Override
        public void describeTo(Description description) {
            appendDestinations(description, expectedDestinations);
        }

        private static void appendDestinations(Description description, Destination[] destinations) {
            description.appendText("destinations:");

            for (Destination destination : destinations) {
                description.appendText("\n\t\t").appendValue(destination).appendText("with");

                for (Link link : destination.getInLinks()) {
                    description.appendText("\n\t\t\t").appendValue(link);
                }
            }
        }

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Tests
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Parameterized.Parameters(name = "file: {0}")
    public static Collection<Object[]> generateFiles() {
        return Arrays.asList(new Object[][] {
                {file(), destinations()},  // empty file
                {file(""), destinations()},
                {file("0|10|C"), destinations(
                        destination(0, neighbor(10, customerLabel()))
                )},
                {file("0|10|C", "0|11|C"), destinations(
                        destination(0,
                                neighbor(10, customerLabel()),
                                neighbor(11, customerLabel())
                        )
                )},
                {file("0|10|C", "1|10|C"), destinations(
                        destination(0, neighbor(10, customerLabel())),
                        destination(1, neighbor(10, customerLabel()))
                )},
                {file("0|10|C", "1|10|C", "0|11|C"), destinations(
                        destination(0,
                                neighbor(10, customerLabel()),
                                neighbor(11, customerLabel())
                        ),
                        destination(1, neighbor(10, customerLabel()))
                )},
        });
    }

    @Test
    public void readAll_Successful() throws Exception {
        Reader inputFileReader = new StringReader(fileContent);

        try (AnycastReader reader = new AnycastReader(inputFileReader, topology)) {
            assertThat(reader.readAll(), isDestinations(expectedDestinations));
        }
    }
}