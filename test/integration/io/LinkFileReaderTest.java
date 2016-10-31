package io;

import core.UnlabelledLink;
import org.junit.Test;

import java.io.File;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LinkFileReaderTest {

    /**
     * Readable way to access the test file. Takes the name of the test file and returns a file object with
     * for the corresponding test file. It includes as the parent directory the directory containing all
     * test files for this test case.
     */
    private static File file(String filename) {
        return new File("test/integration/io/link_file_reader", filename);
    }

    /**
     * Readable way to create an Unlabelled link.
     */
    private static UnlabelledLink link(int srcId, int destId) {
        return new UnlabelledLink(srcId, destId);
    }

    @Test
    public void readAllLinks_CleanLinkFile_ReadAllLinksInTheFile() throws Exception {

        UnlabelledLink[] expectedLinks = {
                link(1, 2), link(1, 3), link(2, 3), link(10, 22)
        };

        try (LinkFileReader reader = new LinkFileReader(file("clean_link_file.txt"))) {
            assertThat(reader.readAllLinks(), is(asList(expectedLinks)));
        }
    }

    @Test
    public void readAllLinks_DirtyValidLinkFile_ReadOnlyValidLinksInTheFile() throws Exception {

        UnlabelledLink[] expectedLinks = {
                link(1, 2), link(1, 3), link(2, 3), link(10, 22)
        };

        try (LinkFileReader reader = new LinkFileReader(file("dirty_valid_link_file.txt"))) {
            assertThat(reader.readAllLinks(), is(asList(expectedLinks)));
        }
    }

}