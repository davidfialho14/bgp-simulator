package io;

import core.exporters.AnycastMap;
import core.topology.ConnectedNode;
import core.topology.Label;
import core.topology.Topology;

import java.io.*;

/**
 * The anycast file reader implements the reading logic of anycast files. It has one simple public method read(),
 * which takes an anycast file an returns an anycast map. It must be associated with the topology to which the file
 * refers to.
 * Anycast associations are given line by line in the format: destination|node|attribute|path_length
 *      destination: ID of the destination to anycast
 *      node:        node actually advertising the destination
 *      attribute:   attribute advertised by the node
 *      path_length: path length advertised by the node
 */
public class AnycastFileReader implements Closeable {

    private final BufferedReader reader;
    private final Topology topology;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Constructors
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public AnycastFileReader(File anycastFile, Topology topology) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(anycastFile));
        this.topology = topology;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Public Interface
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * Reads an anycast file and returns the corresponding anycast map.
     *
     * @return anycast map corresponding to the anycast file.
     */
    public AnycastMap read() throws IOException, ParseException {

        AnycastMap anycastMap = new AnycastMap();

        int lineCount = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            // the line format is <destination>|<neighbour>|<relationship>
            String[] lineArgs = line.split("\\|");

            if (lineArgs.length != 3) {
                throw new ParseException("expected 3 arguments but got " + lineArgs.length, lineCount);
            }

            // TODO improve error detecting

            try {
                int destinationId = Integer.parseInt(lineArgs[0]);
                int neighbourId = Integer.parseInt(lineArgs[1]);
                Label label = topology.getPolicy().createLabel(lineArgs[2]);

                ConnectedNode neighbour = topology.getNetwork().getNode(neighbourId);
                if (neighbour == null) {
                    throw new ParseException("anycast neighbour not found in the network");
                }

                anycastMap.put(destinationId, neighbour, label);

            } catch (NumberFormatException e) {
                throw new ParseException("nodes' IDs must be non-negative integer values");
            }

            lineCount++;
        }

        return  anycastMap;
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     * <p>
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
