package io;

import core.UnlabelledLink;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reader to read a file containing a set of links from a network.
 */
public class LinkFileReader implements AutoCloseable {

    private final File linkFile;
    private final BufferedReader reader;

    /**
     * Creates the reader and opens the given file. Behaves in the same way as the constructor of any other
     * standard java Reader.
     *
     * @param linkFile file containing the links to read
     * @throws FileNotFoundException if the link file does not exist
     */
    public LinkFileReader(File linkFile) throws FileNotFoundException {
        this.linkFile = linkFile;
        reader = new BufferedReader(new FileReader(linkFile));
    }

    public List<UnlabelledLink> readAllLinks() throws IOException {
        return Files.lines(Paths.get(linkFile.getPath()))
                    .map(LinkFileReader::lineToLink)
                    .filter(link -> link != null)
                    .collect(Collectors.toList());
    }

    private static UnlabelledLink lineToLink(String line) {
        String[] splitLine = line.split("\\|");

        try {
            int sourceId = Integer.parseInt(splitLine[0]);
            int destinationId = Integer.parseInt(splitLine[1]);

            return new UnlabelledLink(sourceId, destinationId);

        } catch (NumberFormatException e) {
            // this line is invalid
            return null;
        }

    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

}
