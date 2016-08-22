package io.networkreaders;

import core.topology.Network;
import core.topology.Policy;
import core.topology.Topology;
import core.topology.exceptions.NodeNotFoundException;
import io.networkreaders.exceptions.ParseException;
import policies.Policies;

import java.io.*;

/**
 * Topology reader implementation for the simple topology file format.
 * The simple topology format stores the topology link by link. Each line contains a link given in the
 * format: <source>|<destination>|<relationship>
 * source: id of the source node
 * destination: id of the destination node
 * relationship: relationship label
 * <p>
 * The first line is special because it contains the routing policy tag.
 */
public class SimpleTopologyReader implements TopologyReader {

    private final BufferedReader fileReader;    // file reader used to parse the file

    /**
     * Creates a new SimpleTopologyReader, given the File to read from.
     *
     * @param file the file to read from
     * @throws FileNotFoundException if the file can not be open for reading
     */
    public SimpleTopologyReader(File file) throws FileNotFoundException {
        fileReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Reads a single topology from a simple topology file format.
     *
     * @return topology associating the topology and policy read
     */
    @Override
    public Topology read() throws IOException, ParseException, NodeNotFoundException {

        // policy is specified in the first line
        Policy policy = Policies.getPolicy(fileReader.readLine());
        Network network = new Network();

        // the next lines, each defines a link
        String line;
        int lineCount = 2;  // stores the number of the last line read from the file
        while ((line = fileReader.readLine()) != null) {
            // the line format is <source>|<destination>|<relationship>
            String[] lineArgs = line.split("\\|");

            if (lineArgs.length != 3) {
                throw new ParseException("invalid link format", lineCount);
            }

            int sourceId = Integer.parseInt(lineArgs[0]);
            int destinationId = Integer.parseInt(lineArgs[1]);
            String label = lineArgs[2];

            network.addNode(sourceId);
            network.addNode(destinationId);
            network.addLink(sourceId, destinationId, policy.createLabel(label));

            lineCount++;
        }

        return new Topology(network, policy);
    }

    @Override
    public void close() throws IOException {
        fileReader.close();
    }

}
