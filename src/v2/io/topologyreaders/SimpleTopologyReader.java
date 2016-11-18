package v2.io.topologyreaders;


import v2.core.Label;
import v2.core.Policy;
import v2.core.Router;
import v2.core.Topology;
import v2.core.protocols.Detection;
import v2.io.topologyreaders.exceptions.InvalidPolicyTagException;
import v2.io.topologyreaders.exceptions.TopologyParseException;

import java.io.*;

import static v2.core.protocols.SSBGPProtocol.ssBGPProtocol;

/**
 * Topology reader implementation for the simple topology file format.
 *
 * The simple topology starts with a line containing the routing policy: "policy=(policy_tag)"
 * Followed by multiple router lines: "router=(routerID)|(MRAI)|(Detection)"
 * Followed by multiple link lines: "link=(neighbor)|(label)"
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
        Policy policy = PolicyTagger.getPolicy(fileReader.readLine());
        Topology topology = new Topology(policy, ssBGPProtocol());

        //
        // Read the following lines to build the topology
        //

        String line;
        int lineCount = 0;
        Router currentRouter = null;

        while ((line = fileReader.readLine()) != null) {
            lineCount++;

            // ignore empty lines
            if (line.isEmpty()) continue;

            LineEntry lineEntry = parseLine(line, lineCount);

            switch (lineEntry.key) {
                case "router":
                    currentRouter = parseRouter(lineEntry, lineCount);
                    topology.addRouter(currentRouter);
                    break;

                case "link":
                    LinkEntry linkEntry = parseLink(lineEntry, topology, currentRouter, lineCount);
                    topology.link(currentRouter, linkEntry.neighbor, linkEntry.label);
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
        private final Router neighbor;
        private final Label label;

        private LinkEntry(Router neighbor, Label label) {
            this.neighbor = neighbor;
            this.label = label;
        }
    }

    private LineEntry parseLine(String line, int lineNumber) throws TopologyParseException {
        line.replaceAll("\\s", "");    // remove all whitespaces

        // split key from values
        String[] splitKeyAndValues = line.split("=");

        if (splitKeyAndValues.length != 2) {
            throw new TopologyParseException("Invalid key/value pair", lineNumber);
        }

        return new LineEntry(splitKeyAndValues[0], splitKeyAndValues[1].split(":"));
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

    private LinkEntry parseLink(LineEntry lineEntry, Topology currentTopology, Router currentRouter,
                                int lineNumber) throws TopologyParseException {

        if (lineEntry.values.length != 2) {
            throw new TopologyParseException("Invalid missing some link key values", lineNumber);
        }

        try {
            int neighborID = Integer.parseInt(lineEntry.values[0]);
            Label label = currentTopology.getPolicy().createLabel(lineEntry.values[1]);

            Router neighbor = currentTopology.getRouter(neighborID);
            if (neighbor == null) {
                throw new TopologyParseException("Invalid router " + neighborID, lineNumber);
            }

            return new LinkEntry(neighbor, label);

        } catch (NumberFormatException | InvalidPolicyTagException e) {
            throw new TopologyParseException("Invalid link value", lineNumber);
        }

    }

}
