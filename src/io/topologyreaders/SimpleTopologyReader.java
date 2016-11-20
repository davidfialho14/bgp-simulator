package io.topologyreaders;


import core.Label;
import core.Policy;
import core.Router;
import core.Topology;
import core.exceptions.RouterNotFoundException;
import core.protocols.Detection;
import io.topologyreaders.exceptions.InvalidPolicyTagException;
import io.topologyreaders.exceptions.TopologyParseException;

import java.io.*;

import static core.protocols.SSBGPProtocol.ssBGPProtocol;

/**
 * Topology reader implementation for the simple topology file format.
 *
 * The simple topology starts with a line containing the routing policy: "policy=(policy_tag)"
 * Followed by multiple router lines: "router=(routerID)|(MRAI)|(Detection)"
 * Followed by multiple link lines: "link=(sourceID)|(targetID)|(label)"
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
    public Topology read() throws IOException, TopologyParseException {

        // policy is specified in the first line
        LineEntry policyEntry = parseLine(fileReader.readLine(), 1);
        Policy policy = PolicyTagger.getPolicy(policyEntry.values[0]);

        Topology topology = new Topology(policy, ssBGPProtocol());

        //
        // Read the following lines to build the topology
        //

        String line;
        int lineCount = 1;

        while ((line = fileReader.readLine()) != null) {
            lineCount++;

            // ignore empty lines
            if (line.isEmpty()) continue;

            LineEntry lineEntry = parseLine(line, lineCount);

            switch (lineEntry.key) {
                case "router":
                    topology.addRouter(parseRouter(lineEntry, lineCount));
                    break;

                case "link":
                    LinkEntry linkEntry = parseLink(lineEntry, topology, lineCount);

                    try {
                        topology.link(linkEntry.sourceID, linkEntry.targetID, linkEntry.label);
                    } catch (RouterNotFoundException e) {
                        throw new TopologyParseException(e.getMessage(), lineCount);
                    }

                    break;

                default:
                    throw new TopologyParseException("Invalid line key '" + lineEntry.key + "'", lineCount);
            }

        }

        return topology;
    }

    @Override
    public void close() throws IOException {
        fileReader.close();
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *
     *  Implementation Private Helpers
     *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private static class LineEntry {
        private final String key;
        private final String[] values;

        private LineEntry(String key, String[] values) {
            this.key = key;
            this.values = values;
        }
    }

    private static class LinkEntry {
        private final int sourceID;
        private final int targetID;
        private final Label label;

        public LinkEntry(int sourceID, int targetID, Label label) {
            this.sourceID = sourceID;
            this.targetID = targetID;
            this.label = label;
        }
    }

    private LineEntry parseLine(String line, int lineNumber) throws TopologyParseException {
        line = line.replaceAll("\\s", "");    // remove all whitespaces

        // split key from values
        String[] splitKeyAndValues = line.split("=");

        if (splitKeyAndValues.length != 2) {
            throw new TopologyParseException("Invalid key/value pair", lineNumber);
        }

        return new LineEntry(splitKeyAndValues[0], splitKeyAndValues[1].split("\\|"));
    }

    private Router parseRouter(LineEntry lineEntry, int lineNumber)
            throws TopologyParseException {

        if (lineEntry.values.length != 3) {
            throw new TopologyParseException("Invalid missing some router key values", lineNumber);
        }

        try {
            int routerID = Integer.parseInt(lineEntry.values[0]);
            int MRAI = Integer.parseInt(lineEntry.values[1]);
            Detection detection = Detection.parseDetection(lineEntry.values[2]);

            return new Router(routerID, MRAI, detection);

        } catch (NumberFormatException | TopologyParseException e) {
            throw new TopologyParseException("Invalid router value", lineNumber);
        }
    }

    private LinkEntry parseLink(LineEntry lineEntry, Topology currentTopology, int lineNumber)
            throws TopologyParseException {

        if (lineEntry.values.length != 3) {
            throw new TopologyParseException("Invalid missing some link key values", lineNumber);
        }

        try {
            int sourceID = Integer.parseInt(lineEntry.values[0]);
            int targetID = Integer.parseInt(lineEntry.values[1]);
            Label label = currentTopology.getPolicy().createLabel(lineEntry.values[2]);

            return new LinkEntry(sourceID, targetID, label);

        } catch (NumberFormatException | InvalidPolicyTagException e) {
            throw new TopologyParseException("Invalid link value", lineNumber);
        }

    }

}
